package com.panthydev.m2batteryapp;

import android.app.Application;
import android.util.Log;

import com.panthydev.m2batteryapp.Managers.CloudSyncPreferences;
import com.panthydev.m2batteryapp.Managers.DataManager;
import com.panthydev.m2batteryapp.data.DataCollection.WorkHandler;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Ensure the data collection chain is bootstrapped whenever the app process starts.
        try {
            WorkHandler.EnsureRunning(this);
        } catch (Exception e) {
            Log.e("App", "Failed to ensure data collection running", e);
        }

        // Start cloud sync automatically on app launch, but only when an endpoint is configured.
        //TESTING, USE THIS COMMAND IN TERMINAL BEFORE TESTING, IT STARTS A MOCK SERVER
        //Set-ExecutionPolicy -Scope Process Bypass
        //.\mock_sync_server.ps1
        CloudSyncPreferences.SetSyncEndpoint(this, "http://10.0.2.2:18080/sync/"); // TODO: remove this line; it's just for testing
        String endpoint = CloudSyncPreferences.GetSyncEndpoint(this);
        if (endpoint != null && !endpoint.isEmpty()) {
            DataManager.SyncDatabaseToCloudAsync(this, false, result ->
                    Log.d("App", "Auto sync finished: success=" + result.success
                            + ", code=" + result.httpCode
                            + ", msg=" + result.message));
        } else {
            Log.d("App", "Auto sync skipped: no backend endpoint configured");
        }
    }

}
