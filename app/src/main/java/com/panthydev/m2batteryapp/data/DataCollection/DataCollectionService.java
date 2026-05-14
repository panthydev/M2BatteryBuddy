package com.panthydev.m2batteryapp.data.DataCollection;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.panthydev.m2batteryapp.Managers.NotificationWorker;
import com.panthydev.m2batteryapp.R;

public class DataCollectionService extends Service {

    private static final String TAG = "DataCollectionService";
    private static final String CHANNEL_ID = "data_collection_channel";
    private static final int NOTIFICATION_ID = 1001;
    private static final long INTERVAL_MS = 60_000L;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable collectRunnable = new Runnable() {
        @Override
        public void run() {
            if (!running) {
                return;
            }

            try {
                SystemDataCollector collector = new SystemDataCollector(getApplicationContext());
                if (!DataWorker.CollectedAllApps(getApplicationContext())) {
                    collector.CollectAndSendUsageDataToDB();
                    DataWorker.SetConditions(getApplicationContext(), true);
                }

                collector.CollectAndSendBatteryDataToDB();
                collector.appDischargeTimer();

                try{

                    NotificationWorker notificationWorker = new NotificationWorker(getApplicationContext());
                    notificationWorker.NotifWorkerEntryPoint();
                } catch (Exception e) {

                }


                Log.d(TAG, "Collected data at " + System.currentTimeMillis());
            } catch (Exception e) {
                Log.e(TAG, "Collection tick failed", e);
            }

            handler.postDelayed(this, INTERVAL_MS);
        }

    };

    private boolean running = false;

    @Override
    public void onCreate() {
        super.onCreate();
        createChannel();
        startForeground(NOTIFICATION_ID, buildNotification());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!running) {
            running = true;
            handler.removeCallbacks(collectRunnable);
            handler.post(collectRunnable);
            Log.d(TAG, "Foreground collection loop started");
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        running = false;
        handler.removeCallbacks(collectRunnable);
        Log.d(TAG, "Foreground collection loop stopped");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Battery Buddy Data Collection",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel.setDescription("Keeps Battery Buddy collecting data every minute");
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    private android.app.Notification buildNotification() {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Battery Buddy running")
                .setContentText("Collecting battery data every minute")
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();
    }
}

