package com.panthydev.m2batteryapp.data.DataCollection;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.panthydev.m2batteryapp.Managers.NotificationWorker;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        try {
            var sysDataCollector = new SystemDataCollector(context);
            if(!CollectedAllApps(context)){
                sysDataCollector.CollectAndSendUsageDataToDB();
                SetConditions(context, true);
            }

            var time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date());
            Log.d("TEST", "Starting work at: " + time);

            sysDataCollector.CollectAndSendBatteryDataToDB();
            sysDataCollector.appDischargeTimer();

            // Run Notification Worker // THIS IS NOT WORKING
            NotificationWorker notificationWorker = new NotificationWorker(context);
            notificationWorker.NotifWorkerEntryPoint();


            return Result.success();
        } catch (Exception e) {
            Log.e("DataWorker", "Data collection failed", e);
            return Result.retry();
        }
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
