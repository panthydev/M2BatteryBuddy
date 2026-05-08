package com.panthydev.m2batteryapp.Notifications;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.google.android.material.button.MaterialButton;
import com.panthydev.m2batteryapp.R;

public class NotificationSender extends AppCompatActivity {
    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean o) {
            if(o){
                Toast.makeText(NotificationSender.this, "Post notification granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(NotificationSender.this, "Post notification not granted", Toast.LENGTH_SHORT).show();
            }
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton postNotification = findViewById(R.id.postNotification);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationSender.this,"test")
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Notification text content")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        postNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && ActivityCompat.checkSelfPermission(NotificationSender.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    activityResultLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel notificationChannel = new NotificationChannel("test", getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);
                        notificationChannel.setDescription("Notification Description...");
                        notificationManager.createNotificationChannel(notificationChannel);
                        notificationManager.notify(10, builder.build());
                    }

                }
            }
        });
    }
}
