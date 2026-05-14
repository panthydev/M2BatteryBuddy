package com.panthydev.m2batteryapp.Notifications;


import static androidx.core.content.ContextCompat.startActivity;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.panthydev.m2batteryapp.MainActivity;
import com.panthydev.m2batteryapp.R;

/**
 *
 */
public class NotificationSender {

    private Context context;
    private static final String CHANNEL_ID = "test";

    // Constructor — pass in context from any Activity
    public NotificationSender(Context context) {
        this.context = context;
        createChannel(); // set up the channel on creation
    }


    /**
     * Call this to create and send notifications
     * @param title what is the title
     * @param message the msg that will go into the notification
     * example
     * NotificationSender notificationSender = new NotificationSender(this);
     * notificationSender.send ("Reminder", "Don't forget your task!");
     */
    // Call this to send a notification
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    public void send(String title, String message, int ID) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat NMC = NotificationManagerCompat.from(context);
        NMC.notify(ID, builder.build());
    }

    // Private helper — creates the channel once
    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("App notifications");

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
    }
}
