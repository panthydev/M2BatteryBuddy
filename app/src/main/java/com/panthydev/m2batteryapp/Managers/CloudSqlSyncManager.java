package com.panthydev.m2batteryapp.Managers;

import android.content.Context;

import com.panthydev.m2batteryapp.BackendClient.BackendHttpClient;
import com.panthydev.m2batteryapp.BackendClient.PayloadBuilder;
import com.panthydev.m2batteryapp.DataBase.DbHelper;
import com.panthydev.m2batteryapp.data.DataObjects.App;
import com.panthydev.m2batteryapp.data.DataObjects.BatteryData;
import com.panthydev.m2batteryapp.data.DataObjects.DataPack;

import org.json.JSONObject;

import java.util.UUID;

public final class CloudSqlSyncManager {

    private CloudSqlSyncManager() {}

    public static CloudSyncResult SyncDatabase(Context context, String endpointUrl, boolean fullResync) {
        String resolvedEndpoint = endpointUrl;
        if (resolvedEndpoint == null || resolvedEndpoint.isEmpty()) {
            resolvedEndpoint = CloudSyncPreferences.GetSyncEndpoint(context);
        }

        String batchId = UUID.randomUUID().toString();
        if (resolvedEndpoint == null || resolvedEndpoint.isEmpty()) {
            return CloudSyncResult.failure(-1, "Missing sync endpoint URL", batchId, fullResync);
        }

        DataPack<BatteryData> batteryData;
        DataPack<App> appData;

        try (DbHelper dbHelper = new DbHelper(context)) {
            batteryData = fullResync
                    ? dbHelper.GetAllBatteryData()
                    : dbHelper.GetBatteryDataSince(CloudSyncPreferences.GetLastBatterySyncTimestamp(context));
            appData = fullResync
                    ? dbHelper.GetAllAppData()
                    : dbHelper.GetAppDataSince(null, CloudSyncPreferences.GetLastAppSyncTimestamp(context));

            if (batteryData == null) {
                batteryData = new DataPack<>();
            }
            if (appData == null) {
                appData = new DataPack<>();
            }

            batteryData.PrepareForTransport();
            appData.PrepareForTransport();
        }

        try {
            JSONObject payload = PayloadBuilder.buildPayload(context, batteryData, appData, batchId, fullResync);
            BackendHttpClient.BackendHttpResponse response = BackendHttpClient.postJson(resolvedEndpoint, payload);
            boolean ok = response.httpCode >= 200 && response.httpCode < 300;

            CloudSyncResult result = ok
                    ? CloudSyncResult.success(response.httpCode, response.body, batteryData.dataList.size(), appData.dataList.size(), batchId, fullResync)
                    : CloudSyncResult.failure(response.httpCode, response.body, batchId, fullResync);

            if (result.success) {
                CloudSyncCheckpointManager.UpdateCheckpoints(context, batteryData, appData);
            }

            return result;
        } catch (Exception e) {
            return CloudSyncResult.failure(-1, e.getMessage(), batchId, fullResync);
        }
    }
}
