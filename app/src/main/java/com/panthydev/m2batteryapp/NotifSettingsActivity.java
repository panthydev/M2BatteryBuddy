package com.panthydev.m2batteryapp;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.panthydev.m2batteryapp.Managers.NotificationManager;

public class NotifSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notif_settings);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        SwitchPowersave60percent.setChecked(prefSwitchPowersave60percent);
//        SwitchPowersave50percent.setChecked(prefSwitchPowersave50percent);
//        SwitchPowersave40percent.setChecked(prefSwitchPowersave40percent);
//        SwitchPowersave30percent.setChecked(prefSwitchPowersave30percent);
//        SwitchPowersave20percent.setChecked(prefSwitchPowersave20percent);
//        SwitchPowersave10percent.setChecked(prefSwitchPowersave10percent);
//
//        SwitchPowersave3Hours.setChecked(prefSwitchPowersave3Hours);
//        SwitchPowersave25Hours.setChecked(prefSwitchPowersave25Hours);
//        SwitchPowersave2Hours.setChecked(prefSwitchPowersave2Hours);
//        SwitchPowersave15Hours.setChecked(prefSwitchPowersave15Hours);
//        SwitchPowersave1Hour.setChecked(prefSwitchPowersave1Hour);
//        SwitchPowersave30Min.setChecked(prefSwitchPowersave30Min);
//
//        Switch60percent.setChecked(prefSwitch60percent);
//        Switch50percent.setChecked(prefSwitch50percent);
//        Switch40percent.setChecked(prefSwitch40percent);
//        Switch30percent.setChecked(prefSwitch30percent);
//        Switch20percent.setChecked(prefSwitch20percent);
//        Switch10percent.setChecked(prefSwitch10percent);
//
//        Switch3Hours.setChecked(prefSwitch3Hours);
//        Switch25Hours.setChecked(prefSwitch25Hours);
//        Switch2Hours.setChecked(prefSwitch2Hours);
//        Switch15Hours.setChecked(prefSwitch15Hours);
//        Switch1Hour.setChecked(prefSwitch1Hour);
//        Switch30Min.setChecked(prefSwitch30Min);
//
//        SwitchTips.setChecked(prefSwitchTips);

        BottomNavigationView topNavigationView = findViewById(R.id.top_menu_bar);
        topNavigationView.setSelectedItemId(R.id.notif_settings_butt);

        topNavigationView.setOnItemSelectedListener(item -> {
            int itemId= item.getItemId();
            if (itemId == R.id.notif_settings_butt)
            {
             return true;
            } else if (itemId == R.id.app_settings_butt)
            {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                overridePendingTransition(R.anim.ani_fade_enter, R.anim.ani_fade_exit);
                finish();
                return true;
            }
            return false;
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.menu_bar);
        bottomNavigationView.setSelectedItemId(R.id.settings_butt);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home_butt)
            {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                overridePendingTransition(R.anim.ani_fade_enter, R.anim.ani_fade_exit);
                finish();
                return true;
            } else if (itemId == R.id.info_butt)
            {
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                overridePendingTransition(R.anim.ani_fade_enter, R.anim.ani_fade_exit);
                finish();
                return true;
            } else if (itemId == R.id.settings_butt)
            {
                return true;
            }
            return false;
        });
    }

//   /// Switch methods ///
//    //Percent and powersave
//    SwitchCompat SwitchPowersave60percent = findViewById(R.id.SwitchPowerSavePercent);
//    boolean prefSwitchPowersave60percent = NotificationManager.GetSwitchPowersave60percent(this);
//    public void CheckSwitchPowersave60percent () {
//        if (!prefSwitchPowersave60percent) {
//            NotificationManager.SetSwitchPowersave60percent(this, prefSwitchPowersave60percent = true);
//            SwitchPowersave60percent.setChecked(prefSwitchPowersave60percent);
//        }
//        else {
//            NotificationManager.SetSwitchPowersave60percent(this, prefSwitchPowersave60percent = false);
//            SwitchPowersave60percent.setChecked(prefSwitchPowersave60percent);
//        }
//    }
//    SwitchCompat SwitchPowersave50percent = findViewById(R.id.SwitchPowerSavePercent2);
//    boolean prefSwitchPowersave50percent = NotificationManager.GetSwitchPowersave50percent(this);
//    public void CheckSwitchPowersave50percent () {
//        if (!prefSwitchPowersave50percent) {
//            NotificationManager.SetSwitchPowersave50percent(this, prefSwitchPowersave50percent = true);
//            SwitchPowersave50percent.setChecked(prefSwitchPowersave50percent);
//        }
//        else {
//            NotificationManager.SetSwitchPowersave50percent(this, prefSwitchPowersave50percent = false);
//            SwitchPowersave50percent.setChecked(prefSwitchPowersave50percent);
//        }
//    }
//    SwitchCompat SwitchPowersave40percent = findViewById(R.id.SwitchPowerSavePercent3);
//    boolean prefSwitchPowersave40percent = NotificationManager.GetSwitchPowersave40percent(this);
//    public void CheckSwitchPowersave40percent () {
//        if (!prefSwitchPowersave40percent) {
//            NotificationManager.SetSwitchPowersave40percent(this, prefSwitchPowersave40percent = true);
//            SwitchPowersave40percent.setChecked(prefSwitchPowersave40percent);
//        }
//        else {
//            NotificationManager.SetSwitchPowersave40percent(this, prefSwitchPowersave40percent = false);
//            SwitchPowersave40percent.setChecked(prefSwitchPowersave40percent);
//        }
//    }
//    SwitchCompat SwitchPowersave30percent = findViewById(R.id.SwitchPowerSavePercent4);
//    boolean prefSwitchPowersave30percent = NotificationManager.GetSwitchPowersave30percent(this);
//    public void CheckSwitchPowersave30percent () {
//        if (!prefSwitchPowersave30percent) {
//            NotificationManager.SetSwitchPowersave30percent(this, prefSwitchPowersave30percent = true);
//            SwitchPowersave30percent.setChecked(prefSwitchPowersave30percent);
//        }
//        else {
//            NotificationManager.SetSwitchPowersave30percent(this, prefSwitchPowersave30percent = false);
//            SwitchPowersave30percent.setChecked(prefSwitchPowersave30percent);
//        }
//    }
//    SwitchCompat SwitchPowersave20percent = findViewById(R.id.SwitchPowerSavePercent5);
//    boolean prefSwitchPowersave20percent = NotificationManager.GetSwitchPowersave20percent(this);
//    public void CheckSwitchPowersave20percent () {
//        if (!prefSwitchPowersave20percent) {
//            NotificationManager.SetSwitchPowersave20percent(this, prefSwitchPowersave20percent = true);
//            SwitchPowersave20percent.setChecked(prefSwitchPowersave20percent);
//        }
//        else {
//            NotificationManager.SetSwitchPowersave20percent(this, prefSwitchPowersave20percent = false);
//            SwitchPowersave20percent.setChecked(prefSwitchPowersave20percent);
//        }
//    }
//    SwitchCompat SwitchPowersave10percent = findViewById(R.id.SwitchPowerSavePercent6);
//    boolean prefSwitchPowersave10percent = NotificationManager.GetSwitchPowersave10percent(this);
//    public void CheckSwitchPowersave10percent () {
//        if (!prefSwitchPowersave10percent) {
//            NotificationManager.SetSwitchPowersave10percent(this, prefSwitchPowersave10percent = true);
//            SwitchPowersave10percent.setChecked(prefSwitchPowersave10percent);
//        }
//        else {
//            NotificationManager.SetSwitchPowersave10percent(this, prefSwitchPowersave10percent = false);
//            SwitchPowersave10percent.setChecked(prefSwitchPowersave10percent);
//        }
//    }
//
//    //Hours and powersave
//    SwitchCompat SwitchPowersave3Hours = findViewById(R.id.SwitchPSHour);
//    boolean prefSwitchPowersave3Hours = NotificationManager.GetSwitchPowersave3hours(this);
//    public void CheckSwitchPowersave3Hours () {
//        if (!prefSwitchPowersave3Hours) {
//            NotificationManager.SetSwitchPowersave3hours(this, prefSwitchPowersave3Hours = true);
//            SwitchPowersave3Hours.setChecked(prefSwitchPowersave3Hours);
//        }
//        else {
//            NotificationManager.SetSwitchPowersave3hours(this, prefSwitchPowersave3Hours = false);
//            SwitchPowersave3Hours.setChecked(prefSwitchPowersave3Hours);
//        }
//    }
//    SwitchCompat SwitchPowersave25Hours = findViewById(R.id.SwitchPSHour2);
//    boolean prefSwitchPowersave25Hours = NotificationManager.GetSwitchPowersave25hours(this);
//    public void CheckSwitchPowersave25Hours () {
//        if (!prefSwitchPowersave25Hours) {
//            NotificationManager.SetSwitchPowersave25hours(this, prefSwitchPowersave25Hours = true);
//            SwitchPowersave25Hours.setChecked(prefSwitchPowersave25Hours);
//        }
//        else {
//            NotificationManager.SetSwitchPowersave25hours(this, prefSwitchPowersave25Hours = false);
//            SwitchPowersave25Hours.setChecked(prefSwitchPowersave25Hours);
//        }
//    }
//    SwitchCompat SwitchPowersave2Hours = findViewById(R.id.switchPSHour3);
//    boolean prefSwitchPowersave2Hours = NotificationManager.GetSwitchPowersave2hours(this);
//    public void CheckSwitchPowersave2Hours () {
//        if (!prefSwitchPowersave2Hours) {
//            NotificationManager.SetSwitchPowersave2hours(this, prefSwitchPowersave2Hours = true);
//            SwitchPowersave2Hours.setChecked(prefSwitchPowersave2Hours);
//        }
//        else {
//            NotificationManager.SetSwitchPowersave2hours(this, prefSwitchPowersave2Hours = false);
//            SwitchPowersave2Hours.setChecked(prefSwitchPowersave2Hours);
//        }
//    }
//    SwitchCompat SwitchPowersave15Hours = findViewById(R.id.switchPSHour4);
//    boolean prefSwitchPowersave15Hours = NotificationManager.GetSwitchPowersave15hours(this);
//    public void CheckSwitchPowersave15Hours () {
//        if (!prefSwitchPowersave15Hours) {
//            NotificationManager.SetSwitchPowersave15hours(this, prefSwitchPowersave15Hours = true);
//            SwitchPowersave15Hours.setChecked(prefSwitchPowersave15Hours);
//        }
//        else {
//            NotificationManager.SetSwitchPowersave15hours(this, prefSwitchPowersave15Hours = false);
//            SwitchPowersave15Hours.setChecked(prefSwitchPowersave15Hours);
//        }
//    }
//    SwitchCompat SwitchPowersave1Hour = findViewById(R.id.switchPSHour5);
//    boolean prefSwitchPowersave1Hour = NotificationManager.GetSwitchPowersave1hours(this);
//    public void CheckSwitchPowersave1Hour () {
//        if (!prefSwitchPowersave1Hour) {
//            NotificationManager.SetSwitchPowersave1hours(this, prefSwitchPowersave1Hour = true);
//            SwitchPowersave1Hour.setChecked(prefSwitchPowersave1Hour);
//        }
//        else {
//            NotificationManager.SetSwitchPowersave1hours(this, prefSwitchPowersave1Hour = false);
//            SwitchPowersave1Hour.setChecked(prefSwitchPowersave1Hour);
//        }
//    }
//    SwitchCompat SwitchPowersave30Min = findViewById(R.id.switchPSHour6);
//    boolean prefSwitchPowersave30Min = NotificationManager.GetSwitchPowersave30min(this);
//    public void CheckSwitchPowersave30Min () {
//        if (!prefSwitchPowersave30Min) {
//            NotificationManager.SetSwitchPowersave30min(this, prefSwitchPowersave30Min = true);
//            SwitchPowersave30Min.setChecked(prefSwitchPowersave30Min);
//        }
//        else {
//            NotificationManager.SetSwitchPowersave30min(this, prefSwitchPowersave30Min = false);
//            SwitchPowersave30Min.setChecked(prefSwitchPowersave30Min);
//        }
//    }
//
//
//    //Percent
//    SwitchCompat Switch60percent = findViewById(R.id.SwitchPercent);
//    boolean prefSwitch60percent = NotificationManager.GetSwitch60percent(this);
//    public void CheckSwitch60percent () {
//        if (!prefSwitch60percent) {
//            NotificationManager.SetSwitch60percent(this, prefSwitch60percent = true);
//            Switch60percent.setChecked(prefSwitch60percent);
//        }
//        else {
//            NotificationManager.SetSwitch60percent(this, prefSwitch60percent = false);
//            Switch60percent.setChecked(prefSwitch60percent);
//        }
//    }
//    SwitchCompat Switch50percent = findViewById(R.id.switchPercent2);
//    boolean prefSwitch50percent = NotificationManager.GetSwitch50percent(this);
//    public void CheckSwitch50percent () {
//        if (!prefSwitch50percent) {
//            NotificationManager.SetSwitch50percent(this, prefSwitch50percent = true);
//            Switch50percent.setChecked(prefSwitch50percent);
//        }
//        else {
//            NotificationManager.SetSwitch50percent(this, prefSwitch50percent = false);
//            Switch50percent.setChecked(prefSwitch50percent);
//        }
//    }
//    SwitchCompat Switch40percent = findViewById(R.id.switchPercent3);
//    boolean prefSwitch40percent = NotificationManager.GetSwitch40percent(this);
//    public void CheckSwitch40percent () {
//        if (!prefSwitch40percent) {
//            NotificationManager.SetSwitch40percent(this, prefSwitch40percent = true);
//            Switch40percent.setChecked(prefSwitch40percent);
//        }
//        else {
//            NotificationManager.SetSwitch40percent(this, prefSwitch40percent = false);
//            Switch40percent.setChecked(prefSwitch40percent);
//        }
//    }
//    SwitchCompat Switch30percent = findViewById(R.id.switchPercent4);
//    boolean prefSwitch30percent = NotificationManager.GetSwitch30percent(this);
//    public void CheckSwitch30percent () {
//        if (!prefSwitch30percent) {
//            NotificationManager.SetSwitch30percent(this, prefSwitch30percent = true);
//            Switch30percent.setChecked(prefSwitch30percent);
//        }
//        else {
//            NotificationManager.SetSwitch30percent(this, prefSwitch30percent = false);
//            Switch30percent.setChecked(prefSwitch30percent);
//        }
//    }
//    SwitchCompat Switch20percent = findViewById(R.id.switchPercent5);
//    boolean prefSwitch20percent = NotificationManager.GetSwitch20percent(this);
//    public void CheckSwitch20percent () {
//        if (!prefSwitch20percent) {
//            NotificationManager.SetSwitch20percent(this, prefSwitch20percent = true);
//            Switch20percent.setChecked(prefSwitch20percent);
//        }
//        else {
//            NotificationManager.SetSwitch20percent(this, prefSwitch20percent = false);
//            Switch20percent.setChecked(prefSwitch20percent);
//        }
//    }
//    SwitchCompat Switch10percent = findViewById(R.id.switchPercent6);
//    boolean prefSwitch10percent = NotificationManager.GetSwitch10percent(this);
//    public void CheckSwitch10percent () {
//        if (!prefSwitch10percent) {
//            NotificationManager.SetSwitch10percent(this, prefSwitch10percent = true);
//            Switch10percent.setChecked(prefSwitch10percent);
//        }
//        else {
//            NotificationManager.SetSwitch10percent(this, prefSwitch10percent = false);
//            Switch10percent.setChecked(prefSwitch10percent);
//        }
//    }
//
//    //Hours
//    SwitchCompat Switch3Hours = findViewById(R.id.SwitchHour);
//    boolean prefSwitch3Hours = NotificationManager.GetSwitch3hours(this);
//    public void CheckSwitch3Hours () {
//        if (!prefSwitch3Hours) {
//            NotificationManager.SetSwitch3hours(this, prefSwitch3Hours = true);
//            Switch3Hours.setChecked(prefSwitch3Hours);
//        }
//        else {
//            NotificationManager.SetSwitch3hours(this, prefSwitch3Hours = false);
//            Switch3Hours.setChecked(prefSwitch3Hours);
//        }
//    }
//    SwitchCompat Switch25Hours = findViewById(R.id.switchHour2);
//    boolean prefSwitch25Hours = NotificationManager.GetSwitch25hours(this);
//    public void CheckSwitch25Hours () {
//        if (!prefSwitch25Hours) {
//            NotificationManager.SetSwitch25hours(this, prefSwitch25Hours = true);
//            Switch25Hours.setChecked(prefSwitch25Hours);
//        }
//        else {
//            NotificationManager.SetSwitch25hours(this, prefSwitch25Hours = false);
//            Switch25Hours.setChecked(prefSwitch25Hours);
//        }
//    }
//    SwitchCompat Switch2Hours = findViewById(R.id.switchHour3);
//    boolean prefSwitch2Hours = NotificationManager.GetSwitch2hours(this);
//    public void CheckSwitch2Hours () {
//        if (!prefSwitch2Hours) {
//            NotificationManager.SetSwitch2hours(this, prefSwitch2Hours = true);
//            Switch2Hours.setChecked(prefSwitch2Hours);
//        }
//        else {
//            NotificationManager.SetSwitch2hours(this, prefSwitch2Hours = false);
//            Switch2Hours.setChecked(prefSwitch2Hours);
//        }
//    }
//    SwitchCompat Switch15Hours = findViewById(R.id.switchHour4);
//    boolean prefSwitch15Hours = NotificationManager.GetSwitch15hours(this);
//    public void CheckSwitch15Hours () {
//        if (!prefSwitch15Hours) {
//            NotificationManager.SetSwitch15hours(this, prefSwitch15Hours = true);
//            Switch15Hours.setChecked(prefSwitch15Hours);
//        }
//        else {
//            NotificationManager.SetSwitch15hours(this, prefSwitch15Hours = false);
//            Switch15Hours.setChecked(prefSwitch15Hours);
//        }
//    }
//    SwitchCompat Switch1Hour = findViewById(R.id.switchHour5);
//    boolean prefSwitch1Hour = NotificationManager.GetSwitch1hours(this);
//    public void CheckSwitch1Hour () {
//        if (!prefSwitch1Hour) {
//            NotificationManager.SetSwitch1hours(this, prefSwitch1Hour = true);
//            Switch1Hour.setChecked(prefSwitch1Hour);
//        }
//        else {
//            NotificationManager.SetSwitch1hours(this, prefSwitch1Hour = false);
//            Switch1Hour.setChecked(prefSwitch1Hour);
//        }
//    }
//    SwitchCompat Switch30Min = findViewById(R.id.switchHour6);
//    boolean prefSwitch30Min = NotificationManager.GetSwitch30min(this);
//    public void CheckSwitch30Min () {
//        if (!prefSwitch30Min) {
//            NotificationManager.SetSwitch30min(this, prefSwitch30Min = true);
//            Switch30Min.setChecked(prefSwitch30Min);
//        }
//        else {
//            NotificationManager.SetSwitch30min(this, prefSwitch30Min = false);
//            Switch30Min.setChecked(prefSwitch30Min);
//        }
//    }
//
//    //Tips
//    SwitchCompat SwitchTips = findViewById(R.id.SwitchTips);
//    boolean prefSwitchTips = NotificationManager.GetSwitchTips(this);
//    public void CheckSwitchTips () {
//        if (!prefSwitchTips) {
//            NotificationManager.SetSwitchTips(this, prefSwitchTips = true);
//            SwitchTips.setChecked(prefSwitchTips);
//        }
//        else {
//            NotificationManager.SetSwitchTips(this, prefSwitchTips = false);
//            SwitchTips.setChecked(prefSwitchTips);
//        }
//    }

}