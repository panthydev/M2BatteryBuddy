package com.panthydev.m2batteryapp;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.panthydev.m2batteryapp.Managers.NotificationManager;
import com.panthydev.m2batteryapp.data.DataCollection.WorkHandler;

import android.provider.Settings;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    boolean appsCollectedStarted;
    boolean intervalStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.menu_bar);
        bottomNavigationView.setSelectedItemId(R.id.home_butt);

        bottomNavigationView.setOnItemSelectedListener(item-> {
            int itemId = item.getItemId();
            if (itemId == R.id.home_butt)
            {
                return true;
            } else if (itemId == R.id.info_butt)
            {
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                overridePendingTransition(R.anim.ani_fade_enter, R.anim.ani_fade_exit);
                finish();                return true;
            } else if (itemId == R.id.settings_butt)
            {
                Intent intent = new Intent(getApplicationContext(), NotifSettingsActivity.class);
                intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                overridePendingTransition(R.anim.ani_fade_enter, R.anim.ani_fade_exit);
                finish();
                return true;
            }
            return false;
        });

        PercentageGraph percentageGraph = new PercentageGraph(this);

        ConstraintLayout bar = findViewById(R.id.barGraphContainer);
        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
                intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                overridePendingTransition(R.anim.ani_fade_enter, R.anim.ani_fade_exit);
                finish();
            }
        });

        appsCollectedStarted = NotificationManager.GetFirstAppCollectionOn(this);
        isAccessGranted();
        if (!isAccessGranted() && !appsCollectedStarted) {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }

        intervalStarted = NotificationManager.GetIntervalOn(this);
        if (!intervalStarted) {
            WorkHandler workHandler = new WorkHandler();
            workHandler.StartDataCollection(this);
            NotificationManager.SetIntervalOn(this, intervalStarted = true);
        }

    }

    private boolean isAccessGranted() {
        try {
            PackageManager packageManager = getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            int mode = 0;

            mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationInfo.uid, applicationInfo.packageName);
            NotificationManager.SetFirstAppCollectionOn(this, appsCollectedStarted = true);
            return (mode == AppOpsManager.MODE_ALLOWED);

        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}