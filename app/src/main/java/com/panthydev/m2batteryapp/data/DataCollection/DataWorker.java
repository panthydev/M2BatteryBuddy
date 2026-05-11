package com.panthydev.m2batteryapp.data.DataCollection;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

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

        //TODO create SystemDataCollector
        //TODO run relevant methods from it


        sysDataCollector.CollectAndSendBatteryDataToDB();
        sysDataCollector.appDischargeTimer();

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
