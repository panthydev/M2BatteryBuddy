package com.panthydev.m2batteryapp.Notifications;

import android.Manifest;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.panthydev.m2batteryapp.R;

public class NotificationPermission extends AppCompatActivity {
    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean o) {
            if(o){
                Toast.makeText(NotificationPermission.this, "Post notification granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(NotificationPermission.this, "Post notification not granted", Toast.LENGTH_SHORT).show();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && ActivityCompat.checkSelfPermission(NotificationPermission.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            activityResultLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        }
    }
}
