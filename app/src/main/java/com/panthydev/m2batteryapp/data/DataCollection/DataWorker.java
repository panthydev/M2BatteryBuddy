package com.panthydev.m2batteryapp.data.DataCollection;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class DataWorker extends Worker{
    public DataWorker(@NonNull Context context, @NonNull WorkerParameters workerParams)
    {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork()
    {

        //TODO create SystemDataCollector
        //TODO run relevant methods from it
        return Result.success();
    }
}
