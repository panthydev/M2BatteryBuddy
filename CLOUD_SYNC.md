# Cloud sync for M2BatteryBuddy

This project now includes a lightweight cloud-sync layer for exporting the local SQLite database to a backend endpoint.

## Important note
Android apps should **not** connect directly to Google Cloud SQL with database credentials.
Instead, point the app at an HTTPS backend such as:
- Cloud Run
- Cloud Functions
- Your own API server

That backend can then insert the received rows into Cloud SQL.

## Main classes
- `Managers/CloudSqlSyncManager.java` - builds the sync payload and POSTs it to the backend endpoint.
- `Managers/CloudSyncPreferences.java` - stores the endpoint URL, sender ID, and last successful sync timestamps.
- `Managers/CloudSyncResult.java` - small result object for sync success/failure.

## How the payload is structured
The upload contains:
- `metadata` with device model, manufacturer, sender ID, batch ID, upload timestamp, and sync mode.
- `batteryRows` containing local battery records.
- `appRows` containing local app records.

Every row also includes upload metadata such as:
- `syncBatchId`
- `senderId`
- `deviceLabel`
- `uploadedAt`
- `syncMode`
- `sourceTable`

## How to use it
1. Save your backend endpoint once:
   - `CloudSyncPreferences.SetSyncEndpoint(context, "https://your-api.example.com/sync")`
2. Trigger a sync:
   - `DataManager.SyncDatabaseToCloudAsync(context, true, callback)` for a full export
   - `DataManager.SyncDatabaseToCloudAsync(context, false, callback)` for incremental sync

## Notes
- Incremental sync uses timestamps stored in shared preferences as checkpoints.
- Local DB reads still use the existing `DbHelper`, `DataPack`, and mappers.
- The local schema is unchanged; the extra cloud metadata is added only in the uploaded payload.

