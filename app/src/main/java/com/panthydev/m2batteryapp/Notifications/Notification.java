package com.panthydev.m2batteryapp.Notifications;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.panthydev.m2batteryapp.R;

public class Notification extends AppCompatActivity {
private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission() new ActivityResultCallback<Boolean>);
@Override
    public void onActivityResult(Boolean o){
    if(o){
        Toast.makeText(this, "Post notification granted", Toast.LENGTH_SHORT).show();
    }
    else {
        Toast.makeText(this, "Post notification not granted", Toast.LENGTH_SHORT).show();
    }


}

@Override
protected void onCreate(Bundle savedInstancrState){
    super.onCreate(savedInstancrState);
    setContentView(R.layout.activity_main);
}
}
