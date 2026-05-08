package com.panthydev.m2batteryapp.data.DataCollection;

import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.concurrent.TimeUnit;

public class WorkHandler {
    /**-
     * ++++
     * CALL ONLY ONCE!!!
     */
    public void StartDataCollection(){
        WorkRequest DataCollectionWork = new PeriodicWorkRequest.Builder(DataWorker.class, 1, TimeUnit.MINUTES).build();
        WorkManager.getInstance().enqueue(DataCollectionWork);

    }
}
