package com.panthydev.m2batteryapp.Managers;

import android.content.ContentValues;
import android.content.Context;
import android.os.Build;

import com.panthydev.m2batteryapp.DataBase.DbHelper;
import com.panthydev.m2batteryapp.Interfaces.Mapper;
import com.panthydev.m2batteryapp.data.DataObjects.App;
import com.panthydev.m2batteryapp.data.DataObjects.BatteryData;
import com.panthydev.m2batteryapp.data.DataObjects.DataObject;
import com.panthydev.m2batteryapp.data.DataObjects.DataPack;
import com.panthydev.m2batteryapp.data.DataObjects.Mappers.AppMapper;
import com.panthydev.m2batteryapp.data.DataObjects.Mappers.BatteryDataMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public final class CloudSqlSyncManager {

    // IMPORTANT:
    // Android apps should not connect directly to Cloud SQL with database credentials.
    // This manager is intentionally built as an HTTPS sync client.
    // Point it at a backend endpoint (Cloud Run / Cloud Functions / your own API)
    // that writes the received rows into Cloud SQL.
    private static final int CONNECT_TIMEOUT_MS = 15_000;
    private static final int READ_TIMEOUT_MS = 30_000;

    private static final BatteryDataMapper BATTERY_MAPPER = new BatteryDataMapper();
    private static final AppMapper APP_MAPPER = new AppMapper();

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

        DbHelper dbHelper = new DbHelper(context);
        DataPack<BatteryData> batteryData = fullResync
                ? dbHelper.GetAllBatteryData()
                : dbHelper.GetBatteryDataSince(CloudSyncPreferences.GetLastBatterySyncTimestamp(context));
        DataPack<App> appData = fullResync
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

        try {
            JSONObject payload = buildPayload(context, batchId, fullResync, batteryData, appData);
            CloudSyncResult result = postJson(resolvedEndpoint, payload, batchId, fullResync, batteryData.dataList.size(), appData.dataList.size());

            if (result.success) {
                updateCheckpoints(context, batteryData, appData);
            }

            return result;
        } catch (Exception e) {
            return CloudSyncResult.failure(-1, e.getMessage(), batchId, fullResync);
        }
    }

    private static JSONObject buildPayload(Context context,
                                           String batchId,
                                           boolean fullResync,
                                           DataPack<BatteryData> batteryData,
                                           DataPack<App> appData) throws Exception {
        JSONObject payload = new JSONObject();
        JSONObject metadata = new JSONObject();

        String uploadedAt = nowString();
        metadata.put("batchId", batchId);
        metadata.put("senderId", CloudSyncPreferences.GetOrCreateSenderId(context));
        metadata.put("deviceManufacturer", Build.MANUFACTURER);
        metadata.put("deviceModel", Build.MODEL);
        metadata.put("deviceLabel", Build.MANUFACTURER + " " + Build.MODEL);
        metadata.put("packageName", context.getPackageName());
        metadata.put("androidVersion", Build.VERSION.RELEASE);
        metadata.put("sdkInt", Build.VERSION.SDK_INT);
        metadata.put("uploadedAt", uploadedAt);
        metadata.put("syncMode", fullResync ? "FULL" : "INCREMENTAL");
        metadata.put("sourceDatabase", "DB");

        payload.put("metadata", metadata);
        payload.put("batteryRows", buildRows("batteryTable", batteryData, BATTERY_MAPPER, batchId, context, fullResync, uploadedAt));
        payload.put("appRows", buildRows("appTable", appData, APP_MAPPER, batchId, context, fullResync, uploadedAt));

        return payload;
    }

    private static <T extends DataObject> JSONArray buildRows(String tableName,
                                                              DataPack<T> dataPack,
                                                              Mapper<T> mapper,
                                                              String batchId,
                                                              Context context,
                                                              boolean fullResync,
                                                              String uploadedAt) throws Exception {
        JSONArray array = new JSONArray();
        String senderId = CloudSyncPreferences.GetOrCreateSenderId(context);
        String deviceLabel = Build.MANUFACTURER + " " + Build.MODEL;

        for (T item : dataPack.dataList) {
            ContentValues values = mapper.ToContentValues(item);
            JSONObject row = contentValuesToJson(values);
            row.put("syncBatchId", batchId);
            row.put("senderId", senderId);
            row.put("deviceLabel", deviceLabel);
            row.put("uploadedAt", uploadedAt);
            row.put("syncMode", fullResync ? "FULL" : "INCREMENTAL");
            row.put("sourceTable", tableName);
            array.put(row);
        }

        return array;
    }

    private static JSONObject contentValuesToJson(ContentValues values) throws Exception {
        JSONObject row = new JSONObject();
        for (Map.Entry<String, Object> entry : values.valueSet()) {
            Object value = entry.getValue();
            if (value == null) {
                row.put(entry.getKey(), JSONObject.NULL);
            } else {
                row.put(entry.getKey(), value);
            }
        }
        return row;
    }

    private static CloudSyncResult postJson(String endpointUrl,
                                            JSONObject payload,
                                            String batchId,
                                            boolean fullResync,
                                            int batteryRowCount,
                                            int appRowCount) throws IOException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(endpointUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(CONNECT_TIMEOUT_MS);
            connection.setReadTimeout(READ_TIMEOUT_MS);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setRequestProperty("Accept", "application/json");

            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8))) {
                writer.write(payload.toString());
                writer.flush();
            }

            int responseCode = connection.getResponseCode();
            String responseBody = readResponse(connection, responseCode);
            boolean ok = responseCode >= 200 && responseCode < 300;

            if (ok) {
                return CloudSyncResult.success(responseCode, responseBody, batteryRowCount, appRowCount, batchId, fullResync);
            }
            return CloudSyncResult.failure(responseCode, responseBody, batchId, fullResync);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static String readResponse(HttpURLConnection connection, int responseCode) throws IOException {
        BufferedReader reader = null;
        try {
            if (responseCode >= 200 && responseCode < 400) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            } else {
                if (connection.getErrorStream() == null) {
                    return "";
                }
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
            }

            if (reader == null) {
                return "";
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private static void updateCheckpoints(Context context, DataPack<BatteryData> batteryData, DataPack<App> appData) {
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

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(latest);
    }

    private static String nowString() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}


