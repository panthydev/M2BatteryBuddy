package com.panthydev.m2batteryapp.Notifications;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.panthydev.m2batteryapp.R;

public class NotificationsMaker extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            // Create the object — 'this' means "use this Activity as the context"
            NotificationSender notificationSender = new NotificationSender(this);

            // Find your button
            MaterialButton postNotification = findViewById(R.id.postNotification);

            // When button is pressed, send notification
            postNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notificationSender.send(""+NotificationsMessages., ""+NotificationsMessages.);
                }
            });
        }
    }