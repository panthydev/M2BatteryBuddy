package com.panthydev.m2batteryapp.data.DataCollection;

import android.content.Context;

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
    public void StartDataCollection(Context context){
        WorkRequest DataCollectionWork = new PeriodicWorkRequest.Builder(DataWorker.class, 1, TimeUnit.MINUTES).build();
        WorkManager.getInstance(context).enqueue(DataCollectionWork);
    }
}
