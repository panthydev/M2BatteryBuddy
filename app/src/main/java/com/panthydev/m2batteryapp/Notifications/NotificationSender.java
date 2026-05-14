package com.panthydev.m2batteryapp.Notifications;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import androidx.core.app.NotificationCompat;
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
    public void send(String title, String message) {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.baseline_notifications_24)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);

            NotificationManager manager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            manager.notify(10, builder.build());

    }

    // Private helper — creates the channel once
    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("App notifications");

            NotificationManager manager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            manager.createNotificationChannel(channel);

        }
    }
}
