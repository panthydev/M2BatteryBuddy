package com.panthydev.m2batteryapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.panthydev.m2batteryapp.Managers.CloudSqlSyncManager;
import com.panthydev.m2batteryapp.Managers.CloudSyncPreferences;
import com.panthydev.m2batteryapp.data.DataCollection.WorkHandler;

import java.util.concurrent.TimeUnit;

public class App extends Application {

    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();

        // Ensure the data collection chain is bootstrapped whenever the app process starts.
        try {
            WorkHandler.EnsureRunning(this);
        } catch (Exception e) {
            Log.e(TAG, "Failed to ensure data collection running", e);
        }

        // Temporary test endpoint; remove this once you store the endpoint from settings.
        CloudSyncPreferences.SetSyncEndpoint(this, "https://battery-buddy-backend-834594360876.europe-west1.run.app/api/batbuddy/data"); // TODO: remove this line; it's just for testing

        Constraints syncConstraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest periodicSync = new PeriodicWorkRequest.Builder(
                PeriodicCloudSyncWorker.class,
                15,
                TimeUnit.MINUTES
        )
                .setConstraints(syncConstraints)
                .build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                PeriodicCloudSyncWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                periodicSync
        );
    }

    public static class PeriodicCloudSyncWorker extends Worker {
        public static final String WORK_NAME = "CloudSyncPeriodicWork";
        private static final String WORK_TAG = "PeriodicCloudSyncWorker";

        public PeriodicCloudSyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
            super(context, workerParams);
        }

        @NonNull
        @Override
        public Result doWork() {
            Context context = getApplicationContext();
            String endpoint = CloudSyncPreferences.GetSyncEndpoint(context);

            if (endpoint == null || endpoint.isEmpty()) {
                Log.d(WORK_TAG, "Skipping sync: no endpoint configured");
                return Result.success();
            }

            try {
                var result = CloudSqlSyncManager.SyncDatabase(context, endpoint, false);
                Log.d(WORK_TAG, "Periodic sync finished: success=" + result.success
                        + ", code=" + result.httpCode
                        + ", msg=" + result.message);
            } catch (Exception e) {
                Log.e(WORK_TAG, "Periodic sync failed", e);
            }

            return Result.success();
        }
    }

}
