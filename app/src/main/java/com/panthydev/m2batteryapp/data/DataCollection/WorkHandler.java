package com.panthydev.m2batteryapp.data.DataCollection;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import android.util.Log;

import androidx.work.WorkManager;

import com.panthydev.m2batteryapp.data.DataCollection.DataCollectionService;

public class WorkHandler {
    public static final String DATA_COLLECTION_WORK_NAME = "DataCollectionWork";
    public static final String DATA_COLLECTION_WORK_NAME_SLOT_A = "DataCollectionWorkSlotA";
    public static final String DATA_COLLECTION_WORK_NAME_SLOT_B = "DataCollectionWorkSlotB";
    public static final String INPUT_USE_SLOT_A = "useSlotA";

    /**-
     * ++++
     * CALL ONLY ONCE!!!
     */
    public void StartDataCollection(Context context){
        cancelLegacyWork(context);

        Intent intent = new Intent(context, DataCollectionService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);

        } else {
            context.startService(intent);
        }

        Log.d("WorkHandler", "Foreground data collection service started");
    }

    public static void EnsureRunning(Context context) {
        try {
            Log.d("WorkHandler", "EnsureRunning: starting foreground collection service if needed");
            new WorkHandler().StartDataCollection(context);
        } catch (Exception e) {
            Log.e("WorkHandler", "EnsureRunning check failed; attempting restart", e);
            new WorkHandler().StartDataCollection(context);
        }
    }

    private static void cancelLegacyWork(Context context) {
        WorkManager wm = WorkManager.getInstance(context);
        wm.cancelUniqueWork(DATA_COLLECTION_WORK_NAME);
        wm.cancelUniqueWork(DATA_COLLECTION_WORK_NAME_SLOT_A);
        wm.cancelUniqueWork(DATA_COLLECTION_WORK_NAME_SLOT_B);
    }



}
