package com.panthydev.m2batteryapp.Managers;

import static android.content.Context.BATTERY_SERVICE;

import android.Manifest;
import android.content.Context;
import android.os.BatteryManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;

import com.panthydev.m2batteryapp.MainActivity;
import com.panthydev.m2batteryapp.Notifications.NotificationSender;
import com.panthydev.m2batteryapp.data.DataCollection.SystemDataCollector;

import java.time.Duration;
import java.util.Objects;

public class NotificationWorker {

    Context Syscontext;
    SystemDataCollector SysCollector;

    public NotificationWorker(Context context) {
        this.Syscontext = context;
        this.SysCollector = new SystemDataCollector(context);
        initializePrefs();
    }

    private boolean prefSwitchPowersave60percent, prefSwitchPowersave50percent, prefSwitchPowersave40percent, prefSwitchPowersave30percent, prefSwitchPowersave20percent, prefSwitchPowersave10percent;
    private boolean prefSwitchPowersave3Hours, prefSwitchPowersave25Hours, prefSwitchPowersave2Hours, prefSwitchPowersave15Hours, prefSwitchPowersave1Hour, prefSwitchPowersave30Min;

    private boolean prefSwitch60percent, prefSwitch50percent, prefSwitch40percent, prefSwitch30percent, prefSwitch20percent, prefSwitch10percent;
    private boolean prefSwitch3Hours, prefSwitch25Hours, prefSwitch2Hours, prefSwitch15Hours, prefSwitch1Hour, prefSwitch30Min;

    private boolean prefSwitchTips;

    private boolean PowerSaveOn, IsCharging;
    private int EstBatTimeLeft, BatPercentage;

    private void initializePrefs() {
        prefSwitchPowersave60percent = NotificationManager.GetSwitchPowersave60percent(Syscontext);
        prefSwitchPowersave50percent = NotificationManager.GetSwitchPowersave50percent(Syscontext);
        prefSwitchPowersave40percent = NotificationManager.GetSwitchPowersave40percent(Syscontext);
        prefSwitchPowersave30percent = NotificationManager.GetSwitchPowersave30percent(Syscontext);
        prefSwitchPowersave20percent = NotificationManager.GetSwitchPowersave20percent(Syscontext);
        prefSwitchPowersave10percent = NotificationManager.GetSwitchPowersave10percent(Syscontext);

        prefSwitchPowersave3Hours = NotificationManager.GetSwitchPowersave3hours(Syscontext);
        prefSwitchPowersave25Hours = NotificationManager.GetSwitchPowersave25hours(Syscontext);
        prefSwitchPowersave2Hours = NotificationManager.GetSwitchPowersave2hours(Syscontext);
        prefSwitchPowersave15Hours = NotificationManager.GetSwitchPowersave15hours(Syscontext);
        prefSwitchPowersave1Hour = NotificationManager.GetSwitchPowersave1hours(Syscontext);
        prefSwitchPowersave30Min = NotificationManager.GetSwitchPowersave30min(Syscontext);

        prefSwitch60percent = NotificationManager.GetSwitch60percent(Syscontext);
        prefSwitch50percent = NotificationManager.GetSwitch50percent(Syscontext);
        prefSwitch40percent = NotificationManager.GetSwitch40percent(Syscontext);
        prefSwitch30percent = NotificationManager.GetSwitch30percent(Syscontext);
        prefSwitch20percent = NotificationManager.GetSwitch20percent(Syscontext);
        prefSwitch10percent = NotificationManager.GetSwitch10percent(Syscontext);

        prefSwitch3Hours = NotificationManager.GetSwitch3hours(Syscontext);
        prefSwitch25Hours = NotificationManager.GetSwitch25hours(Syscontext);
        prefSwitch2Hours = NotificationManager.GetSwitch2hours(Syscontext);
        prefSwitch15Hours = NotificationManager.GetSwitch15hours(Syscontext);
        prefSwitch1Hour = NotificationManager.GetSwitch1hours(Syscontext);
        prefSwitch30Min = NotificationManager.GetSwitch30min(Syscontext);

        prefSwitchTips = NotificationManager.GetSwitchTips(Syscontext);

        // Battery State
        BatteryManager BM = (BatteryManager) Syscontext.getSystemService(BATTERY_SERVICE);
        isCharging = BM.isCharging();
        batPercent = SysCollector.batLevelPercent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            batTime = SysCollector.remainingBatLife;
        }
        isPowerSaveOn = SysCollector.powerSaveOn;

        PSNotif3Hours = NotificationManager.GetPowersaveNotOn3HoursLeft(Syscontext);
        PSNotif25Hours = NotificationManager.GetPowersaveNotOn25HoursLeft(Syscontext);
        PSNotif2Hours = NotificationManager.GetPowersaveNotOn2HoursLeft(Syscontext);
        PSNotif15Hours = NotificationManager.GetPowersaveNotOn15HoursLeft(Syscontext);
        PSNotif1Hour = NotificationManager.GetPowersaveNotOn1HourLeft(Syscontext);
        PSNotif30Min = NotificationManager.GetPowersaveNotOn30MinLeft(Syscontext);
        PSNotif60Percent = NotificationManager.GetPowersaveNotOnAt60Percent(Syscontext);
        PSNotif50Percent = NotificationManager.GetPowersaveNotOnAt50Percent(Syscontext);
        PSNotif40Percent = NotificationManager.GetPowersaveNotOnAt40Percent(Syscontext);
        PSNotif30Percent = NotificationManager.GetPowersaveNotOnAt30Percent(Syscontext);
        PSNotif20Percent = NotificationManager.GetPowersaveNotOnAt20Percent(Syscontext);
        PSNotif10Percent = NotificationManager.GetPowersaveNotOnAt10Percent(Syscontext);

        Notif3Hours = NotificationManager.GetPhoneHas3HoursLeft(Syscontext);
        Notif25Hours = NotificationManager.GetPhoneHas25HoursLeft(Syscontext);
        Notif2Hours = NotificationManager.GetPhoneHas2HoursLeft(Syscontext);
        Notif15Hours = NotificationManager.GetPhoneHas15HoursLeft(Syscontext);
        Notif1Hour = NotificationManager.GetPhoneHas1HourLeft(Syscontext);
        Notif30Min = NotificationManager.GetPhoneHas30MinLeft(Syscontext);
        Notif60Percent = NotificationManager.GetPhoneHas60PercentLeft(Syscontext);
        Notif50Percent = NotificationManager.GetPhoneHas50PercentLeft(Syscontext);
        Notif40Percent = NotificationManager.GetPhoneHas40PercentLeft(Syscontext);
        Notif30Percent = NotificationManager.GetPhoneHas30PercentLeft(Syscontext);
        Notif20Percent = NotificationManager.GetPhoneHas20PercentLeft(Syscontext);
        Notif10Percent = NotificationManager.GetPhoneHas10PercentLeft(Syscontext);
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    public void NotifWorkerEntryPoint () {
        BatPercentage = NotificationManager.GetBatteryPercent(Syscontext);
        EstBatTimeLeft = NotificationManager.GetEstBatTime(Syscontext);
        IsCharging = NotificationManager.GetIsCharging(Syscontext);
        PowerSaveOn = NotificationManager.GetPowerSaveOn(Syscontext);

        Log.d("NotificationWorker", "yayy im awake, im gonna do notif stuff now");
        if (prefSwitchPowersave60percent || prefSwitchPowersave50percent || prefSwitchPowersave40percent || prefSwitchPowersave30percent || prefSwitchPowersave20percent || prefSwitchPowersave10percent) {
            SendPSNotifPercent();
        }

        if (prefSwitchPowersave3Hours || prefSwitchPowersave25Hours || prefSwitchPowersave2Hours || prefSwitchPowersave15Hours || prefSwitchPowersave1Hour || prefSwitchPowersave30Min) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                SendPSNotifTime();
            }
        }

        if (prefSwitch60percent || prefSwitch50percent || prefSwitch40percent || prefSwitch30percent || prefSwitch20percent || prefSwitch10percent) {
            SendPercentNotif();
        }

        if (prefSwitch3Hours || prefSwitch25Hours || prefSwitch2Hours || prefSwitch15Hours || prefSwitch1Hour || prefSwitch30Min) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                SendTimeNotif();
            }
        }

        if (prefSwitchTips) {
            SendOtherNotif();
        }
    }

    /// All methods accessable under here ///

    private boolean isCharging;
    private int batPercent;
    private Duration batTime;
    private boolean isPowerSaveOn;

    // Get bools for which notification are turned on from prefs

    private boolean PSNotif3Hours, PSNotif25Hours, PSNotif2Hours, PSNotif15Hours, PSNotif1Hour, PSNotif30Min;
    private boolean PSNotif60Percent, PSNotif50Percent, PSNotif40Percent, PSNotif30Percent, PSNotif20Percent, PSNotif10Percent;

    void ChangeNotifBool(boolean NotifToChange) {
        if (!NotifToChange && !isCharging) {
            NotifToChange = true;
        } else if (NotifToChange && isCharging) {
            NotifToChange = false;
        }
    }

    public void SendPSNotifPercent () {
        switch(batPercent) {
            case 60:
                if (prefSwitchPowersave60percent) {
                    TurnOnPowerSavePercent ();
                    ChangeNotifBool(PSNotif60Percent);
                    NotificationManager.SetPowersaveNotOnAt60Percent(Syscontext, PSNotif60Percent);
                    break;
                }
            case 50:
                if (prefSwitchPowersave50percent) {
                    TurnOnPowerSavePercent();
                    ChangeNotifBool(PSNotif50Percent);
                    NotificationManager.SetPowersaveNotOnAt50Percent(Syscontext, PSNotif50Percent);
                    break;
                }
            case 40:
                if (prefSwitchPowersave40percent) {
                    TurnOnPowerSavePercent();
                    ChangeNotifBool(PSNotif40Percent);
                    NotificationManager.SetPowersaveNotOnAt40Percent(Syscontext, PSNotif40Percent);
                    break;
                }
            case 30:
                if (prefSwitchPowersave30percent) {
                    TurnOnPowerSavePercent();
                    ChangeNotifBool(PSNotif30Percent);
                    NotificationManager.SetPowersaveNotOnAt30Percent(Syscontext, PSNotif30Percent);
                    break;
                }
            case 20:
                if (prefSwitchPowersave20percent) {
                    TurnOnPowerSavePercent();
                    ChangeNotifBool(PSNotif20Percent);
                    NotificationManager.SetPowersaveNotOnAt20Percent(Syscontext, PSNotif20Percent);
                    break;
                }
            case 10:
                if (prefSwitchPowersave10percent) {
                    TurnOnPowerSavePercent();
                    ChangeNotifBool(PSNotif10Percent);
                    NotificationManager.SetPowersaveNotOnAt10Percent(Syscontext, PSNotif10Percent);
                    break;
                }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void SendPSNotifTime () {
        if (Objects.requireNonNull(batTime.toMinutes()) <= 180 && Objects.requireNonNull(batTime.toMinutes()) >= 160 && prefSwitchPowersave3Hours) {
            TurnOnPowerSaveHours();
            ChangeNotifBool(PSNotif3Hours);
            NotificationManager.SetPowersaveNotOn3HoursLeft(Syscontext, PSNotif3Hours);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 150 && Objects.requireNonNull(batTime.toMinutes()) >= 130 && prefSwitchPowersave25Hours) {
            TurnOnPowerSaveHours();
            ChangeNotifBool(PSNotif25Hours);
            NotificationManager.SetPowersaveNotOn25HoursLeft(Syscontext, PSNotif25Hours);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 120 && Objects.requireNonNull(batTime.toMinutes()) >= 100 && prefSwitchPowersave2Hours) {
            TurnOnPowerSaveHours();
            ChangeNotifBool(PSNotif2Hours);
            NotificationManager.SetPowersaveNotOn2HoursLeft(Syscontext, PSNotif2Hours);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 90 && Objects.requireNonNull(batTime.toMinutes()) >= 70 && prefSwitchPowersave15Hours) {
            TurnOnPowerSaveHours();
            ChangeNotifBool(PSNotif15Hours);
            NotificationManager.SetPowersaveNotOn15HoursLeft(Syscontext, PSNotif15Hours);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 60 && Objects.requireNonNull(batTime.toMinutes()) >= 40 && prefSwitchPowersave1Hour) {
            TurnOnPowerSaveHours();
            ChangeNotifBool(PSNotif1Hour);
            NotificationManager.SetPowersaveNotOn1HourLeft(Syscontext, PSNotif1Hour);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 30 && Objects.requireNonNull(batTime.toMinutes()) >= 10 && prefSwitchPowersave30Min) {
            TurnOnPowerSaveHours();
            ChangeNotifBool(PSNotif30Min);
            NotificationManager.SetPowersaveNotOn30MinLeft(Syscontext, PSNotif30Min);
        }
    }


    /**
     * Brug disse to og erstat title samt message med den rigtige context, som jeg tror er Syscontext
     * NotificationSender notificationSender = new NotificationSender(Syscontext);
     * notificationSender.send ("title", "Message", 1);
     */
    void TurnOnPowerSavePercent() {
        if (!isPowerSaveOn && !isCharging) {
            if (PSNotif60Percent) {
                NotificationSender notificationSender = new NotificationSender(Syscontext);
                notificationSender.send ("title", "Message", 1);
            }
            else if (PSNotif50Percent) {
                NotificationSender notificationSender = new NotificationSender(Syscontext);
                notificationSender.send ("title", "Message", 1);
            }
            else if (PSNotif40Percent) {
                NotificationSender notificationSender = new NotificationSender(Syscontext);
                notificationSender.send ("title", "Message", 1);
            }
            else if (PSNotif30Percent) {
                NotificationSender notificationSender = new NotificationSender(Syscontext);
                notificationSender.send ("title", "Message", 1);
            }
            else if (PSNotif20Percent) {
                NotificationSender notificationSender = new NotificationSender(Syscontext);
                notificationSender.send ("title", "Message", 1);
            }
            else if (PSNotif10Percent) {
                NotificationSender notificationSender = new NotificationSender(Syscontext);
                notificationSender.send ("title", "Message", 1);
            }
        }
    }

    void TurnOnPowerSaveHours() {
        if (!isPowerSaveOn && !isCharging) {
            if (PSNotif3Hours) {
                NotificationSender notificationSender = new NotificationSender(Syscontext);
                notificationSender.send ("title", "Message", 1);
            }
            else if (PSNotif25Hours) {
                NotificationSender notificationSender = new NotificationSender(Syscontext);
                notificationSender.send ("title", "Message", 1);
            }
            else if (PSNotif2Hours) {
                NotificationSender notificationSender = new NotificationSender(Syscontext);
                notificationSender.send ("title", "Message", 1);
            }
            else if (PSNotif15Hours) {
                NotificationSender notificationSender = new NotificationSender(Syscontext);
                notificationSender.send ("title", "Message", 1);
            }
            else if (PSNotif1Hour) {
                NotificationSender notificationSender = new NotificationSender(Syscontext);
                notificationSender.send ("title", "Message", 1);
            }
            else if (PSNotif30Min) {
                NotificationSender notificationSender = new NotificationSender(Syscontext);
                notificationSender.send ("title", "Message", 1);
            }
        }
    }


    //////////////////////

    private boolean Notif3Hours, Notif25Hours, Notif2Hours, Notif15Hours, Notif1Hour, Notif30Min;
    private boolean Notif60Percent, Notif50Percent, Notif40Percent, Notif30Percent, Notif20Percent, Notif10Percent;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void SendTimeNotif () {
        if (Objects.requireNonNull(batTime.toMinutes()) <= 180 && Objects.requireNonNull(batTime.toMinutes()) >= 160 && prefSwitch3Hours) {
            InformAboutTime();
            ChangeNotifBool(Notif3Hours);
            NotificationManager.SetPhoneHas3HoursLeft(Syscontext, Notif3Hours);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 150 && Objects.requireNonNull(batTime.toMinutes()) >= 130 && prefSwitch25Hours) {
            InformAboutTime();
            ChangeNotifBool(Notif25Hours);
            NotificationManager.SetPhoneHas25HoursLeft(Syscontext, Notif25Hours);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 120 && Objects.requireNonNull(batTime.toMinutes()) >= 100 && prefSwitch2Hours) {
            InformAboutTime();
            ChangeNotifBool(Notif2Hours);
            NotificationManager.SetPhoneHas2HoursLeft(Syscontext, Notif2Hours);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 90 && Objects.requireNonNull(batTime.toMinutes()) >= 70 && prefSwitch15Hours ) {
            InformAboutTime();
            ChangeNotifBool(Notif15Hours);
            NotificationManager.SetPhoneHas15HoursLeft(Syscontext, Notif15Hours);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 60 && Objects.requireNonNull(batTime.toMinutes()) >= 40 && prefSwitch1Hour) {
            InformAboutTime();
            ChangeNotifBool(Notif1Hour);
            NotificationManager.SetPhoneHas1HourLeft(Syscontext, Notif1Hour);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 30 && Objects.requireNonNull(batTime.toMinutes()) >= 10 && prefSwitch30Min) {
            InformAboutTime();
            ChangeNotifBool(Notif30Min);
            NotificationManager.SetPhoneHas30MinLeft(Syscontext, Notif30Min);
        }
    }

    public void SendPercentNotif () {
        // Here is the assumed bools which are saved in the prefs:
        // GetPowerSaveStatus().something

        switch(batPercent) {
            case 60:
                InformAboutPercent();
                ChangeNotifBool(Notif60Percent);
                NotificationManager.SetPhoneHas60PercentLeft(Syscontext, Notif60Percent);
                break;
            case 50:
                InformAboutPercent();
                ChangeNotifBool(Notif50Percent);
                NotificationManager.SetPhoneHas50PercentLeft(Syscontext, Notif50Percent);
                break;
            case 40:
                InformAboutPercent();
                ChangeNotifBool(Notif40Percent);
                NotificationManager.SetPhoneHas40PercentLeft(Syscontext, Notif40Percent);
                break;
            case 30:
                InformAboutPercent();
                ChangeNotifBool(Notif30Percent);
                NotificationManager.SetPhoneHas30PercentLeft(Syscontext, Notif30Percent);
                break;
            case 20:
                InformAboutPercent();
                ChangeNotifBool(Notif20Percent);
                NotificationManager.SetPhoneHas20PercentLeft(Syscontext, Notif20Percent);
                break;
            case 10:
                InformAboutPercent();
                ChangeNotifBool(Notif10Percent);
                NotificationManager.SetPhoneHas10PercentLeft(Syscontext, Notif10Percent);
                break;
        }
    }

    void InformAboutPercent() {
        if (!isCharging) {
            if (Notif60Percent) {
                NotificationSender notificationSender = new NotificationSender(Syscontext);
                notificationSender.send ("title", "Message", 1);
            }
            else if (Notif50Percent) {
                NotificationSender notificationSender = new NotificationSender(Syscontext);
                notificationSender.send ("title", "Message", 1);
            }
            else if (Notif40Percent) {
                NotificationSender notificationSender = new NotificationSender(Syscontext);
                notificationSender.send ("title", "Message", 1);
            }
            else if (Notif30Percent) {
                NotificationSender notificationSender = new NotificationSender(Syscontext);
                notificationSender.send ("title", "Message", 1);
            }
            else if (Notif20Percent) {
                NotificationSender notificationSender = new NotificationSender(Syscontext);
                notificationSender.send ("title", "Message", 1);
            }
            else if (Notif10Percent) {
                NotificationSender notificationSender = new NotificationSender(Syscontext);
                notificationSender.send ("title", "Message", 1);
            }
        }
    }

    void InformAboutTime() {
        if (!isCharging) {
            if (Notif3Hours) {
                NotificationSender notificationSender = new NotificationSender(Syscontext);
                notificationSender.send ("title", "Message", 1);
            }
            else if (Notif25Hours) {
                NotificationSender notificationSender = new NotificationSender(Syscontext);
                notificationSender.send ("title", "Message", 1);
            }
            else if (Notif2Hours) {
                NotificationSender notificationSender = new NotificationSender(Syscontext);
                notificationSender.send ("title", "Message", 1);
            }
            else if (Notif15Hours) {
                NotificationSender notificationSender = new NotificationSender(Syscontext);
                notificationSender.send ("title", "Message", 1);
            }
            else if (Notif1Hour) {
                NotificationSender notificationSender = new NotificationSender(Syscontext);
                notificationSender.send ("title", "Message", 1);
            }
            else if (Notif30Min) {
                NotificationSender notificationSender = new NotificationSender(Syscontext);
                notificationSender.send ("title", "Message", 1);
            }
        }
    }

    //////////////////////

    int randomInterval = (int)(Math.random() * 101);  // 0 to 100

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    public void SendOtherNotif () {
        if (batPercent > 50 && randomInterval == 1 && prefSwitchTips) {
            NotificationSender notificationSender = new NotificationSender(Syscontext);
            notificationSender.send ("title", "Message", 1);
        }
    }
}
