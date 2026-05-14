package com.panthydev.m2batteryapp;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
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

    SwitchCompat SwitchPowersave60percent;
    boolean prefSwitchPowersave60percent;
    SwitchCompat SwitchPowersave50percent;
    boolean prefSwitchPowersave50percent;
    SwitchCompat SwitchPowersave40percent;
    boolean prefSwitchPowersave40percent;
    SwitchCompat SwitchPowersave30percent;
    boolean prefSwitchPowersave30percent;
    SwitchCompat SwitchPowersave20percent;
    boolean prefSwitchPowersave20percent;
    SwitchCompat SwitchPowersave10percent;
    boolean prefSwitchPowersave10percent;

    boolean prefSwitchPowersave3Hours;
    SwitchCompat SwitchPowersave3Hours;
    boolean prefSwitchPowersave25Hours;
    SwitchCompat SwitchPowersave25Hours;
    boolean prefSwitchPowersave2Hours;
    SwitchCompat SwitchPowersave2Hours;
    boolean prefSwitchPowersave15Hours;
    SwitchCompat SwitchPowersave15Hours;
    boolean prefSwitchPowersave1Hour;
    SwitchCompat SwitchPowersave1Hour;
    boolean prefSwitchPowersave30Min;
    SwitchCompat SwitchPowersave30Min;

    boolean prefSwitch60percent;
    SwitchCompat Switch60percent;
    boolean prefSwitch50percent;
    SwitchCompat Switch50percent;
    boolean prefSwitch40percent;
    SwitchCompat Switch40percent;
    boolean prefSwitch30percent;
    SwitchCompat Switch30percent;
    boolean prefSwitch20percent;
    SwitchCompat Switch20percent;
    boolean prefSwitch10percent;
    SwitchCompat Switch10percent;

    boolean prefSwitch3Hours;
    SwitchCompat Switch3Hours;
    boolean prefSwitch25Hours;
    SwitchCompat Switch25Hours;
    boolean prefSwitch2Hours;
    SwitchCompat Switch2Hours;
    boolean prefSwitch15Hours;
    SwitchCompat Switch15Hours;
    boolean prefSwitch1Hour;
    SwitchCompat Switch1Hour;
    boolean prefSwitch30Min;
    SwitchCompat Switch30Min;

    boolean prefSwitchTips;
    SwitchCompat SwitchTips;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notif_settings);

        SwitchPowersave60percent = findViewById(R.id.SwitchPowerSavePercent);
        prefSwitchPowersave60percent = NotificationManager.GetSwitchPowersave60percent(this);
        SwitchPowersave60percent.setChecked(prefSwitchPowersave60percent);

        SwitchPowersave50percent = findViewById(R.id.SwitchPowerSavePercent2);
        prefSwitchPowersave50percent = NotificationManager.GetSwitchPowersave50percent(this);
        SwitchPowersave50percent.setChecked(prefSwitchPowersave50percent);

        SwitchPowersave40percent = findViewById(R.id.SwitchPowerSavePercent3);
        prefSwitchPowersave40percent = NotificationManager.GetSwitchPowersave40percent(this);
        SwitchPowersave40percent.setChecked(prefSwitchPowersave40percent);

        SwitchPowersave30percent = findViewById(R.id.SwitchPowerSavePercent4);
        prefSwitchPowersave30percent = NotificationManager.GetSwitchPowersave30percent(this);
        SwitchPowersave30percent.setChecked(prefSwitchPowersave30percent);

        SwitchPowersave20percent = findViewById(R.id.SwitchPowerSavePercent5);
        prefSwitchPowersave20percent = NotificationManager.GetSwitchPowersave20percent(this);
        SwitchPowersave20percent.setChecked(prefSwitchPowersave20percent);

        SwitchPowersave10percent = findViewById(R.id.SwitchPowerSavePercent6);
        prefSwitchPowersave10percent = NotificationManager.GetSwitchPowersave10percent(this);
        SwitchPowersave10percent.setChecked(prefSwitchPowersave10percent);


        SwitchPowersave3Hours = findViewById(R.id.SwitchPSHour);
        prefSwitchPowersave3Hours = NotificationManager.GetSwitchPowersave3hours(this);
        SwitchPowersave3Hours.setChecked(prefSwitchPowersave3Hours);

        SwitchPowersave25Hours = findViewById(R.id.SwitchPSHour2);
        prefSwitchPowersave25Hours = NotificationManager.GetSwitchPowersave25hours(this);
        SwitchPowersave25Hours.setChecked(prefSwitch25Hours);

        SwitchPowersave2Hours = findViewById(R.id.switchPSHour3);
        prefSwitchPowersave2Hours = NotificationManager.GetSwitchPowersave2hours(this);
        SwitchPowersave2Hours.setChecked(prefSwitch2Hours);

        SwitchPowersave15Hours = findViewById(R.id.switchPSHour4);
        prefSwitchPowersave15Hours = NotificationManager.GetSwitchPowersave15hours(this);
        SwitchPowersave15Hours.setChecked(prefSwitch15Hours);

        SwitchPowersave1Hour = findViewById(R.id.switchPSHour5);
        prefSwitchPowersave1Hour = NotificationManager.GetSwitchPowersave1hours(this);
        SwitchPowersave1Hour.setChecked(prefSwitch1Hour);

        SwitchPowersave30Min = findViewById(R.id.switchPSHour6);
        prefSwitchPowersave30Min = NotificationManager.GetSwitchPowersave30min(this);
        SwitchPowersave30Min.setChecked(prefSwitch30Min);

        Switch60percent = findViewById(R.id.SwitchPercent);
        prefSwitch60percent = NotificationManager.GetSwitch60percent(this);
        Switch60percent.setChecked(prefSwitch60percent);

        Switch50percent =findViewById(R.id.switchPercent2);
        prefSwitch50percent = NotificationManager.GetSwitch50percent(this);
        Switch50percent.setChecked(prefSwitch50percent);

        Switch40percent = findViewById(R.id.switchPercent3);
        prefSwitch40percent = NotificationManager.GetSwitch40percent(this);
        Switch40percent.setChecked(prefSwitch40percent);

        Switch30percent = findViewById(R.id.switchPercent4);
        prefSwitch30percent = NotificationManager.GetSwitch30percent(this);
        Switch30percent.setChecked(prefSwitch30percent);

        Switch20percent = findViewById(R.id.switchPercent5);
        prefSwitch20percent = NotificationManager.GetSwitch20percent(this);
        Switch20percent.setChecked(prefSwitch20percent);

        Switch10percent =findViewById(R.id.switchPercent6);
        prefSwitch10percent = NotificationManager.GetSwitch10percent(this);
        Switch10percent.setChecked(prefSwitch10percent);

        Switch3Hours = findViewById(R.id.SwitchHour);
        prefSwitch3Hours = NotificationManager.GetSwitch3hours(this);
        Switch3Hours.setChecked(prefSwitch3Hours);

        Switch25Hours =findViewById(R.id.switchHour2);
        prefSwitch25Hours = NotificationManager.GetSwitch25hours(this);
        Switch25Hours.setChecked(prefSwitch25Hours);

        Switch2Hours = findViewById(R.id.switchHour3);
        prefSwitch2Hours = NotificationManager.GetSwitch2hours(this);
        Switch2Hours.setChecked(prefSwitch2Hours);

        Switch15Hours = findViewById(R.id.switchHour4);
        prefSwitch15Hours = NotificationManager.GetSwitch15hours(this);
        Switch15Hours.setChecked(prefSwitch15Hours);

        Switch1Hour =findViewById(R.id.switchHour5);
        prefSwitch1Hour = NotificationManager.GetSwitch1hours(this);
        Switch1Hour.setChecked(prefSwitch1Hour);

        Switch30Min = findViewById(R.id.switchHour6);
        prefSwitch30Min = NotificationManager.GetSwitch30min(this);
        Switch30Min.setChecked(prefSwitch30Min);

        SwitchTips = findViewById(R.id.SwitchTips);
        prefSwitchTips = NotificationManager.GetSwitchTips(this);
        SwitchTips.setChecked(prefSwitchTips);


//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });


        SwitchPowersave25Hours.setChecked(prefSwitchPowersave25Hours);
        SwitchPowersave2Hours.setChecked(prefSwitchPowersave2Hours);
        SwitchPowersave15Hours.setChecked(prefSwitchPowersave15Hours);
        SwitchPowersave1Hour.setChecked(prefSwitchPowersave1Hour);
        SwitchPowersave30Min.setChecked(prefSwitchPowersave30Min);

        Switch60percent.setChecked(prefSwitch60percent);
        Switch50percent.setChecked(prefSwitch50percent);
        Switch40percent.setChecked(prefSwitch40percent);
        Switch30percent.setChecked(prefSwitch30percent);
        Switch20percent.setChecked(prefSwitch20percent);
        Switch10percent.setChecked(prefSwitch10percent);

        Switch3Hours.setChecked(prefSwitch3Hours);
        Switch25Hours.setChecked(prefSwitch25Hours);
        Switch2Hours.setChecked(prefSwitch2Hours);
        Switch15Hours.setChecked(prefSwitch15Hours);
        Switch1Hour.setChecked(prefSwitch1Hour);
        Switch30Min.setChecked(prefSwitch30Min);

        SwitchTips.setChecked(prefSwitchTips);

        BottomNavigationView topNavigationView = findViewById(R.id.top_menu_bar);
        topNavigationView.setSelectedItemId(R.id.notif_settings_butt);

        topNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.notif_settings_butt) {
                return true;
            } else if (itemId == R.id.app_settings_butt) {
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
            if (itemId == R.id.home_butt) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                overridePendingTransition(R.anim.ani_fade_enter, R.anim.ani_fade_exit);
                finish();
                return true;
            } else if (itemId == R.id.info_butt) {
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                overridePendingTransition(R.anim.ani_fade_enter, R.anim.ani_fade_exit);
                finish();
                return true;
            } else if (itemId == R.id.settings_butt) {
                return true;
            }
            return false;
        });
    }

    /// Switch methods ///
    //Percent and powersave
    public void CheckSwitchPowersave60percent(View v) {
        if (!prefSwitchPowersave60percent) {
            NotificationManager.SetSwitchPowersave60percent(this, prefSwitchPowersave60percent = true);
            SwitchPowersave60percent.setChecked(prefSwitchPowersave60percent);
        } else {
            NotificationManager.SetSwitchPowersave60percent(this, prefSwitchPowersave60percent = false);
            SwitchPowersave60percent.setChecked(prefSwitchPowersave60percent);
        }
    }

    public void CheckSwitchPowersave50percent(View v) {
        if (!prefSwitchPowersave50percent) {
            NotificationManager.SetSwitchPowersave50percent(this, prefSwitchPowersave50percent = true);
            SwitchPowersave50percent.setChecked(prefSwitchPowersave50percent);
        } else {
            NotificationManager.SetSwitchPowersave50percent(this, prefSwitchPowersave50percent = false);
            SwitchPowersave50percent.setChecked(prefSwitchPowersave50percent);
        }
    }

    public void CheckSwitchPowersave40percent(View v) {
        if (!prefSwitchPowersave40percent) {
            NotificationManager.SetSwitchPowersave40percent(this, prefSwitchPowersave40percent = true);
            SwitchPowersave40percent.setChecked(prefSwitchPowersave40percent);
        } else {
            NotificationManager.SetSwitchPowersave40percent(this, prefSwitchPowersave40percent = false);
            SwitchPowersave40percent.setChecked(prefSwitchPowersave40percent);
        }
    }

    public void CheckSwitchPowersave30percent(View v) {
        if (!prefSwitchPowersave30percent) {
            NotificationManager.SetSwitchPowersave30percent(this, prefSwitchPowersave30percent = true);
            SwitchPowersave30percent.setChecked(prefSwitchPowersave30percent);
        } else {
            NotificationManager.SetSwitchPowersave30percent(this, prefSwitchPowersave30percent = false);
            SwitchPowersave30percent.setChecked(prefSwitchPowersave30percent);
        }
    }

    public void CheckSwitchPowersave20percent(View v) {
        if (!prefSwitchPowersave20percent) {
            NotificationManager.SetSwitchPowersave20percent(this, prefSwitchPowersave20percent = true);
            SwitchPowersave20percent.setChecked(prefSwitchPowersave20percent);
        } else {
            NotificationManager.SetSwitchPowersave20percent(this, prefSwitchPowersave20percent = false);
            SwitchPowersave20percent.setChecked(prefSwitchPowersave20percent);
        }
    }

    public void CheckSwitchPowersave10percent(View v) {
        if (!prefSwitchPowersave10percent) {
            NotificationManager.SetSwitchPowersave10percent(this, prefSwitchPowersave10percent = true);
            SwitchPowersave10percent.setChecked(prefSwitchPowersave10percent);
        } else {
            NotificationManager.SetSwitchPowersave10percent(this, prefSwitchPowersave10percent = false);
            SwitchPowersave10percent.setChecked(prefSwitchPowersave10percent);
        }
    }

    //Hours and powersave
    public void CheckSwitchPowersave3Hours(View v) {
        if (!prefSwitchPowersave3Hours) {
            NotificationManager.SetSwitchPowersave3hours(this, prefSwitchPowersave3Hours = true);
            SwitchPowersave3Hours.setChecked(prefSwitchPowersave3Hours);
        } else {
            NotificationManager.SetSwitchPowersave3hours(this, prefSwitchPowersave3Hours = false);
            SwitchPowersave3Hours.setChecked(prefSwitchPowersave3Hours);
        }
    }

    public void CheckSwitchPowersave25Hours(View v) {
        if (!prefSwitchPowersave25Hours) {
            NotificationManager.SetSwitchPowersave25hours(this, prefSwitchPowersave25Hours = true);
            SwitchPowersave25Hours.setChecked(prefSwitchPowersave25Hours);
        } else {
            NotificationManager.SetSwitchPowersave25hours(this, prefSwitchPowersave25Hours = false);
            SwitchPowersave25Hours.setChecked(prefSwitchPowersave25Hours);
        }
    }


    public void CheckSwitchPowersave2Hours(View v) {
        if (!prefSwitchPowersave2Hours) {
            NotificationManager.SetSwitchPowersave2hours(this, prefSwitchPowersave2Hours = true);
            SwitchPowersave2Hours.setChecked(prefSwitchPowersave2Hours);
        } else {
            NotificationManager.SetSwitchPowersave2hours(this, prefSwitchPowersave2Hours = false);
            SwitchPowersave2Hours.setChecked(prefSwitchPowersave2Hours);
        }
    }

    public void CheckSwitchPowersave15Hours(View v) {
        if (!prefSwitchPowersave15Hours) {
            NotificationManager.SetSwitchPowersave15hours(this, prefSwitchPowersave15Hours = true);
            SwitchPowersave15Hours.setChecked(prefSwitchPowersave15Hours);
        } else {
            NotificationManager.SetSwitchPowersave15hours(this, prefSwitchPowersave15Hours = false);
            SwitchPowersave15Hours.setChecked(prefSwitchPowersave15Hours);
        }
    }


    public void CheckSwitchPowersave1Hour(View v) {
        if (!prefSwitchPowersave1Hour) {
            NotificationManager.SetSwitchPowersave1hours(this, prefSwitchPowersave1Hour = true);
            SwitchPowersave1Hour.setChecked(prefSwitchPowersave1Hour);
        } else {
            NotificationManager.SetSwitchPowersave1hours(this, prefSwitchPowersave1Hour = false);
            SwitchPowersave1Hour.setChecked(prefSwitchPowersave1Hour);
        }
    }


    public void CheckSwitchPowersave30Min(View v) {
        if (!prefSwitchPowersave30Min) {
            NotificationManager.SetSwitchPowersave30min(this, prefSwitchPowersave30Min = true);
            SwitchPowersave30Min.setChecked(prefSwitchPowersave30Min);
        } else {
            NotificationManager.SetSwitchPowersave30min(this, prefSwitchPowersave30Min = false);
            SwitchPowersave30Min.setChecked(prefSwitchPowersave30Min);
        }
    }


//Percent
    public void CheckSwitch60percent(View v) {
        if (!prefSwitch60percent) {
            NotificationManager.SetSwitch60percent(this, prefSwitch60percent = true);
            Switch60percent.setChecked(prefSwitch60percent);
        } else {
            NotificationManager.SetSwitch60percent(this, prefSwitch60percent = false);
            Switch60percent.setChecked(prefSwitch60percent);
        }
    }


    public void CheckSwitch50percent(View v) {
        if (!prefSwitch50percent) {
            NotificationManager.SetSwitch50percent(this, prefSwitch50percent = true);
            Switch50percent.setChecked(prefSwitch50percent);
        } else {
            NotificationManager.SetSwitch50percent(this, prefSwitch50percent = false);
            Switch50percent.setChecked(prefSwitch50percent);
        }
    }


    public void CheckSwitch40percent(View v) {
        if (!prefSwitch40percent) {
            NotificationManager.SetSwitch40percent(this, prefSwitch40percent = true);
            Switch40percent.setChecked(prefSwitch40percent);
        } else {
            NotificationManager.SetSwitch40percent(this, prefSwitch40percent = false);
            Switch40percent.setChecked(prefSwitch40percent);
        }
    }

    public void CheckSwitch30percent(View v) {
        if (!prefSwitch30percent) {
            NotificationManager.SetSwitch30percent(this, prefSwitch30percent = true);
            Switch30percent.setChecked(prefSwitch30percent);
        } else {
            NotificationManager.SetSwitch30percent(this, prefSwitch30percent = false);
            Switch30percent.setChecked(prefSwitch30percent);
        }
    }


    public void CheckSwitch20percent(View v) {
        if (!prefSwitch20percent) {
            NotificationManager.SetSwitch20percent(this, prefSwitch20percent = true);
            Switch20percent.setChecked(prefSwitch20percent);
        } else {
            NotificationManager.SetSwitch20percent(this, prefSwitch20percent = false);
            Switch20percent.setChecked(prefSwitch20percent);
        }
    }


    public void CheckSwitch10percent(View v) {
        if (!prefSwitch10percent) {
            NotificationManager.SetSwitch10percent(this, prefSwitch10percent = true);
            Switch10percent.setChecked(prefSwitch10percent);
        } else {
            NotificationManager.SetSwitch10percent(this, prefSwitch10percent = false);
            Switch10percent.setChecked(prefSwitch10percent);
        }
    }

    //Hours

    public void CheckSwitch3Hours(View v) {
        if (!prefSwitch3Hours) {
            NotificationManager.SetSwitch3hours(this, prefSwitch3Hours = true);
            Switch3Hours.setChecked(prefSwitch3Hours);
        } else {
            NotificationManager.SetSwitch3hours(this, prefSwitch3Hours = false);
            Switch3Hours.setChecked(prefSwitch3Hours);
        }
    }


    public void CheckSwitch25Hours(View v) {
        if (!prefSwitch25Hours) {
            NotificationManager.SetSwitch25hours(this, prefSwitch25Hours = true);
            Switch25Hours.setChecked(prefSwitch25Hours);
        } else {
            NotificationManager.SetSwitch25hours(this, prefSwitch25Hours = false);
            Switch25Hours.setChecked(prefSwitch25Hours);
        }
    }


    public void CheckSwitch2Hours(View v) {
        if (!prefSwitch2Hours) {
            NotificationManager.SetSwitch2hours(this, prefSwitch2Hours = true);
            Switch2Hours.setChecked(prefSwitch2Hours);
        } else {
            NotificationManager.SetSwitch2hours(this, prefSwitch2Hours = false);
            Switch2Hours.setChecked(prefSwitch2Hours);
        }
    }


    public void CheckSwitch15Hours(View v) {
        if (!prefSwitch15Hours) {
            NotificationManager.SetSwitch15hours(this, prefSwitch15Hours = true);
            Switch15Hours.setChecked(prefSwitch15Hours);
        } else {
            NotificationManager.SetSwitch15hours(this, prefSwitch15Hours = false);
            Switch15Hours.setChecked(prefSwitch15Hours);
        }
    }


    public void CheckSwitch1Hour(View v) {
        if (!prefSwitch1Hour) {
            NotificationManager.SetSwitch1hours(this, prefSwitch1Hour = true);
            Switch1Hour.setChecked(prefSwitch1Hour);
        } else {
            NotificationManager.SetSwitch1hours(this, prefSwitch1Hour = false);
            Switch1Hour.setChecked(prefSwitch1Hour);
        }
    }


    public void CheckSwitch30Min(View v) {
        if (!prefSwitch30Min) {
            NotificationManager.SetSwitch30min(this, prefSwitch30Min = true);
            Switch30Min.setChecked(prefSwitch30Min);
        } else {
            NotificationManager.SetSwitch30min(this, prefSwitch30Min = false);
            Switch30Min.setChecked(prefSwitch30Min);
        }
    }

    //Tips

    public void CheckSwitchTips(View v) {
        if (!prefSwitchTips) {
            NotificationManager.SetSwitchTips(this, prefSwitchTips = true);
            SwitchTips.setChecked(prefSwitchTips);
        } else {
            NotificationManager.SetSwitchTips(this, prefSwitchTips = false);
            SwitchTips.setChecked(prefSwitchTips);
        }
    }
}