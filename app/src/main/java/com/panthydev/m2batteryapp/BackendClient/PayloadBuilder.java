package com.panthydev.m2batteryapp.BackendClient;

import android.content.ContentValues;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.panthydev.m2batteryapp.Interfaces.Mapper;
import com.panthydev.m2batteryapp.Managers.CloudSyncPreferences;
import com.panthydev.m2batteryapp.Utils.Utils;
import com.panthydev.m2batteryapp.data.DataObjects.App;
import com.panthydev.m2batteryapp.data.DataObjects.BatteryData;
import com.panthydev.m2batteryapp.data.DataObjects.DataObject;
import com.panthydev.m2batteryapp.data.DataObjects.DataPack;
import com.panthydev.m2batteryapp.data.DataObjects.Mappers.AppMapper;
import com.panthydev.m2batteryapp.data.DataObjects.Mappers.BatteryDataMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class PayloadBuilder {


    public static JSONObject buildPayload(Context context,
                                          DataPack<BatteryData> batteryData,
                                          DataPack<App> appData,
                                          String batchId,
                                          boolean fullResync) {
        try {
            JSONObject payload = new JSONObject();
            JSONObject metadata = new JSONObject();

            String uploadedAt = Utils.GetStringDateTimeNow();
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
            payload.put("batteryRows", buildRowsFromDataPack(batteryData, new BatteryDataMapper(), batchId, context, fullResync, uploadedAt, "batteryTable"));
            payload.put("appRows", buildRowsFromDataPack(appData, new AppMapper(), batchId, context, fullResync, uploadedAt, "appTable"));

            return payload;
        } catch (Exception ex) {
            // return empty payload on error
            Log.d("PayloadBuilder", "Error building payload: " + ex.getMessage());
            return new JSONObject();
        }
    }

    private static <T extends DataObject> JSONArray buildRowsFromDataPack(DataPack<T> dataPack,
                                                                          Mapper<T> mapper,
                                                                          String batchId,
                                                                          Context context,
                                                                          boolean fullResync,
                                                                          String uploadedAt,
                                                                          String sourceTable) throws Exception {

        JSONArray arr = new JSONArray();
        if (dataPack == null || dataPack.dataList == null) return arr;

        String senderId = CloudSyncPreferences.GetOrCreateSenderId(context);
        String deviceLabel = Build.MANUFACTURER + " " + Build.MODEL;

        for (T item : dataPack.dataList) {
            if (item == null) continue;

            //convert item -> ContentValues -> JSONObject
            ContentValues values = mapper.ToContentValues(item);
            JSONObject row = contentValuesToJson(values);

             row.put("syncBatchId", batchId);
             row.put("senderId", senderId);
             row.put("deviceLabel", deviceLabel);
             row.put("uploadedAt", uploadedAt);
             row.put("syncMode", fullResync ? "FULL" : "INCREMENTAL");
             row.put("sourceTable", sourceTable);
             arr.put(row);
        }
        return arr;
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

}
