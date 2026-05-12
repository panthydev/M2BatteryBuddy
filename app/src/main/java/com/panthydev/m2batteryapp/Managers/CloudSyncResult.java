package com.panthydev.m2batteryapp.Managers;

public class CloudSyncResult {
    public final boolean success;
    public final int httpCode;
    public final String message;
    public final int uploadedBatteryRows;
    public final int uploadedAppRows;
    public final String batchId;
    public final boolean fullResync;

    public CloudSyncResult(boolean success,
                           int httpCode,
                           String message,
                           int uploadedBatteryRows,
                           int uploadedAppRows,
                           String batchId,
                           boolean fullResync) {
        this.success = success;
        this.httpCode = httpCode;
        this.message = message;
        this.uploadedBatteryRows = uploadedBatteryRows;
        this.uploadedAppRows = uploadedAppRows;
        this.batchId = batchId;
        this.fullResync = fullResync;
    }

    public static CloudSyncResult success(int httpCode,
                                          String message,
                                          int uploadedBatteryRows,
                                          int uploadedAppRows,
                                          String batchId,
                                          boolean fullResync) {
        return new CloudSyncResult(true, httpCode, message, uploadedBatteryRows, uploadedAppRows, batchId, fullResync);
    }

    public static CloudSyncResult failure(int httpCode,
                                          String message,
                                          String batchId,
                                          boolean fullResync) {
        return new CloudSyncResult(false, httpCode, message, 0, 0, batchId, fullResync);
    }
}

