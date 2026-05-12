package com.panthydev.m2batteryapp.Managers;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

public final class CloudSyncPreferences {

    private static final String PREF_NAME = "app_prefs";
    private static final String KEY_SYNC_ENDPOINT = "cloud_sync_endpoint";
    private static final String KEY_SYNC_SENDER_ID = "cloud_sync_sender_id";
    private static final String KEY_LAST_BATTERY_SYNC = "cloud_sync_last_battery_timestamp";
    private static final String KEY_LAST_APP_SYNC = "cloud_sync_last_app_timestamp";

    private CloudSyncPreferences() {}

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    private static void putString(Context context, String key, String value) {
        getPrefs(context).edit().putString(key, value).apply();
    }

    private static String getString(Context context, String key, String defaultValue) {
        return getPrefs(context).getString(key, defaultValue);
    }

    public static void SetSyncEndpoint(Context context, String endpointUrl) {
        putString(context, KEY_SYNC_ENDPOINT, endpointUrl);
    }

    public static String GetSyncEndpoint(Context context) {
        return getString(context, KEY_SYNC_ENDPOINT, null);
    }

    public static String GetOrCreateSenderId(Context context) {
        String senderId = getString(context, KEY_SYNC_SENDER_ID, null);
        if (senderId == null || senderId.isEmpty()) {
            senderId = UUID.randomUUID().toString();
            putString(context, KEY_SYNC_SENDER_ID, senderId);
        }
        return senderId;
    }

    public static void SetLastBatterySyncTimestamp(Context context, String timestamp) {
        putString(context, KEY_LAST_BATTERY_SYNC, timestamp);
    }

    public static String GetLastBatterySyncTimestamp(Context context) {
        return getString(context, KEY_LAST_BATTERY_SYNC, null);
    }

    public static void SetLastAppSyncTimestamp(Context context, String timestamp) {
        putString(context, KEY_LAST_APP_SYNC, timestamp);
    }

    public static String GetLastAppSyncTimestamp(Context context) {
        return getString(context, KEY_LAST_APP_SYNC, null);
    }
}

