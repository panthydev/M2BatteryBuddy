package com.panthydev.m2batteryapp.data.DataCollection;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class DataWorker extends Worker{
    public DataWorker(@NonNull Context context, @NonNull WorkerParameters workerParams)
    {
        super(context, workerParams);
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    @NonNull
    @Override
    public Result doWork()
    {

        //TODO create SystemDataCollector
        //TODO run relevant methods from it

        var sysDataCollector = new SystemDataCollector();
        sysDataCollector.CollectAndSendBatteryDataToDB();
        sysDataCollector.appDischargeTimer();


        return Result.success();
    }
}
