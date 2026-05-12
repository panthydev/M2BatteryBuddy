package com.panthydev.m2batteryapp.data.DataCollection;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import androidx.work.OneTimeWorkRequest;
import androidx.work.ExistingWorkPolicy;
import androidx.work.WorkManager;

public class DataWorker extends Worker{

    Context context;
    public DataWorker(@NonNull Context context, @NonNull WorkerParameters workerParams)
    {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork()
    {



        var sysDataCollector = new SystemDataCollector(context);
        if(!CollectedAllApps(context)){
            sysDataCollector.CollectAndSendUsageDataToDB();
            SetConditions(context, true);
        }


        var time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        Log.d("TEST", "Starting work at: " + time);


        //TODO create SystemDataCollector
        //TODO run relevant methods from it


        sysDataCollector.CollectAndSendBatteryDataToDB();
        sysDataCollector.appDischargeTimer();

        // Schedule next run ~1 minute later. Use unique work name to prevent duplicates.
        OneTimeWorkRequest next = new OneTimeWorkRequest.Builder(DataWorker.class)
                .setInitialDelay(1, TimeUnit.MINUTES)
                .build();

        WorkManager.getInstance(getApplicationContext()).enqueueUniqueWork(
                WorkHandler.DATA_COLLECTION_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                next
        );

        return Result.success();
    }





    public static void SetConditions(Context context, boolean b){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("CollectedAllApps", b).apply();
    }

    public static Boolean CollectedAllApps(Context context){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("CollectedAllApps", false);
    }



}
