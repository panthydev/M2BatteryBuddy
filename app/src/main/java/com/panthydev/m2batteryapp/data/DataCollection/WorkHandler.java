package com.panthydev.m2batteryapp.data.DataCollection;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WorkHandler {
    /**-
     * ++++
     * CALL ONLY ONCE!!!
     */
    public void StartDataCollection(Context context){
        WorkRequest DataCollectionWork = new PeriodicWorkRequest.Builder(DataWorker.class, 1, TimeUnit.MINUTES).build();

        var time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        Log.d("TEST", "Starting work at: " + time);
        WorkManager.getInstance(context).enqueue(DataCollectionWork);
    }


}
