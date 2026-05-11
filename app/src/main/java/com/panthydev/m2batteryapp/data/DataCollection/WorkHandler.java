package com.panthydev.m2batteryapp.data.DataCollection;

import android.content.Context;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class WorkHandler {
    public static final String DATA_COLLECTION_WORK_NAME = "DataCollectionWork";

    /**-
     * ++++
     * CALL ONLY ONCE!!!
     */
    public void StartDataCollection(Context context){

        // WorkManager periodic work has a 15-minute minimum interval.
        PeriodicWorkRequest dataCollectionWork = new PeriodicWorkRequest.Builder(
                DataWorker.class,
                15,
                TimeUnit.MINUTES
        ).build();

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                DATA_COLLECTION_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                dataCollectionWork
        );
    }


}
