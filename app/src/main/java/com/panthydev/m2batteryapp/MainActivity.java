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
import com.panthydev.m2batteryapp.Interfaces.Callback;
import com.panthydev.m2batteryapp.Managers.DataManager;
import com.panthydev.m2batteryapp.data.DataCollection.WorkHandler;
import com.panthydev.m2batteryapp.data.DataObjects.BatteryData;
import com.panthydev.m2batteryapp.data.DataObjects.DataPack;
import com.panthydev.m2batteryapp.databinding.ActivityBaseBinding;

import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import java.time.Duration;

public class MainActivity extends AppCompatActivity {
    ActivityBaseBinding binding;
    public TextView batText;
    public TextView batText2;
    public TextView batTextPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); //hvorfor er den her linjer her enlig?
        setContentView(R.layout.activity_main);

        //wat is this. No "main" id in R, so makes error.
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        WorkHandler workHandler = new WorkHandler();

        workHandler.StartDataCollection(this);

        batText = findViewById(R.id.BatTime);
        batText2 = findViewById(R.id.batTime2);
        batTextPercent =findViewById(R.id.textViewUIPercent);

        BatteryUIMethod();

        isAccessGranted();
        if (!isAccessGranted()) {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }


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

    }

    private boolean isAccessGranted() {




        try {
            PackageManager packageManager = getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            int mode = 0;

            mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationInfo.uid, applicationInfo.packageName);
            return (mode == AppOpsManager.MODE_ALLOWED);

        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }

    }


    public void BatteryUIMethod(){
        DataManager.GetBatteryDataAsync(this, 24, new Callback<DataPack<BatteryData>>()
        {
            @Override
            public void OnResult(DataPack<BatteryData> Result)
            {
                int index = Result.dataList.size();
                int hoursRemaining = (int)Result.dataList.get(0).estimatedBatTimeLeft.toHours();
                int minutesRemaining = (int) Result.dataList.get(0).estimatedBatTimeLeft.toMinutes();
                String balls = String.valueOf(minutesRemaining-(hoursRemaining*60));
                String my_ass = String.valueOf(Result.dataList.get(0).estimatedBatTimeLeft.toHours());
                String fuck = String.valueOf(Result.dataList.get(index-1).percentLeft);

                if (Result.dataList.get(0).estimatedBatTimeLeft.getSeconds() == 0)
                {
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run() {
                            batTextPercent.setText(fuck + "%");
                        }
                    });
                }
                else
                {
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run() {
                            batText.setText(my_ass + " Hours" + " &");
                            batText2.setText(balls+ " Minutes");
                        }
                    });
                }

            }
        });
    }
}