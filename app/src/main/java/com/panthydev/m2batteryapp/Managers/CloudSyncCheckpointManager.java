package com.panthydev.m2batteryapp.Managers;

import android.content.Context;

import com.panthydev.m2batteryapp.data.DataObjects.App;
import com.panthydev.m2batteryapp.data.DataObjects.BatteryData;
import com.panthydev.m2batteryapp.data.DataObjects.DataObject;
import com.panthydev.m2batteryapp.data.DataObjects.DataPack;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class CloudSyncCheckpointManager {

    private static final String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private CloudSyncCheckpointManager() {}

    public static void UpdateCheckpoints(Context context, DataPack<BatteryData> batteryData, DataPack<App> appData) {
        String batteryTimestamp = latestTimestamp(batteryData);
        if (batteryTimestamp != null) {
            CloudSyncPreferences.SetLastBatterySyncTimestamp(context, batteryTimestamp);
        }

        String appTimestamp = latestTimestamp(appData);
        if (appTimestamp != null) {
            CloudSyncPreferences.SetLastAppSyncTimestamp(context, appTimestamp);
        }
    }

    private static <T extends DataObject> String latestTimestamp(DataPack<T> dataPack) {
        if (dataPack == null || dataPack.dataList == null || dataPack.dataList.isEmpty()) {
            return null;
        }

        Date latest = null;
        for (T item : dataPack.dataList) {
            if (item == null || item.Timestamp == null) {
                continue;
            }
            if (latest == null || item.Timestamp.after(latest)) {
                latest = item.Timestamp;
            }
        }

        if (latest == null) {
            return null;
        }

        return new SimpleDateFormat(TIMESTAMP_PATTERN, Locale.US).format(latest);
    }
}

