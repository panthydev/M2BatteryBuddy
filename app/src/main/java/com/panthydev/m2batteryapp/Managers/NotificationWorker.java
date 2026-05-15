package com.panthydev.m2batteryapp.Managers;

import static com.panthydev.m2batteryapp.Notifications.NotificationsMessages.*;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;

import com.panthydev.m2batteryapp.Notifications.NotificationSender;
import com.panthydev.m2batteryapp.data.DataCollection.SystemDataCollector;

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

    private boolean isPowerSaveOn, isCharging;
    private int batTime, batPercent;

    private boolean PSNotif3Hours, PSNotif25Hours, PSNotif2Hours, PSNotif15Hours, PSNotif1Hour, PSNotif30Min;
    private boolean PSNotif60Percent, PSNotif50Percent, PSNotif40Percent, PSNotif30Percent, PSNotif20Percent, PSNotif10Percent;

    private boolean Notif3Hours, Notif25Hours, Notif2Hours, Notif15Hours, Notif1Hour, Notif30Min;
    private boolean Notif60Percent, Notif50Percent, Notif40Percent, Notif30Percent, Notif20Percent, Notif10Percent;

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
        batPercent = NotificationManager.GetBatteryPercent(Syscontext);
        batTime = NotificationManager.GetEstBatTime(Syscontext);
        isCharging = NotificationManager.GetIsCharging(Syscontext);
        isPowerSaveOn = NotificationManager.GetPowerSaveOn(Syscontext);

        Log.d("NotificationWorker", "Checking triggers. Bat: " + batPercent + "%, Time: " + batTime + "m, Charging: " + isCharging + ", PS: " + isPowerSaveOn);

        SendPSNotifPercent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            SendPSNotifTime();
            SendTimeNotif();
        }
        SendPercentNotif();
        SendOtherNotif();
    }

    public void SendPSNotifPercent () {
        if (isCharging || isPowerSaveOn) {
            if (isCharging) resetPSPercentFlags();
            return;
        }

        boolean notified = false;
        notified = checkPSPercent(10, prefSwitchPowersave10percent, PSNotif10Percent, M_POWER_SAVE_NOT_ON_10_PERCENT, notified);
        notified = checkPSPercent(20, prefSwitchPowersave20percent, PSNotif20Percent, M_POWER_SAVE_NOT_ON_20_PERCENT, notified);
        notified = checkPSPercent(30, prefSwitchPowersave30percent, PSNotif30Percent, M_POWER_SAVE_NOT_ON_30_PERCENT, notified);
        notified = checkPSPercent(40, prefSwitchPowersave40percent, PSNotif40Percent, M_POWER_SAVE_NOT_ON_40_PERCENT, notified);
        notified = checkPSPercent(50, prefSwitchPowersave50percent, PSNotif50Percent, M_POWER_SAVE_NOT_ON_50_PERCENT, notified);
        notified = checkPSPercent(60, prefSwitchPowersave60percent, PSNotif60Percent, M_POWER_SAVE_NOT_ON_60_PERCENT, notified);
    }

    private boolean checkPSPercent(int threshold, boolean prefEnabled, boolean alreadyNotified, String message, boolean alreadySentThisCycle) {
        if (batPercent <= threshold) {
            if (!alreadyNotified) {
                if (prefEnabled && !alreadySentThisCycle) {
                    new NotificationSender(Syscontext).send(TITLE_POWER_SAVE_IS_NOT_ON, message, 1);
                    alreadySentThisCycle = true;
                }
                setPSPercentFlag(threshold, true);
            }
        } else {
            setPSPercentFlag(threshold, false);
        }
        return alreadySentThisCycle;
    }

    private void setPSPercentFlag(int threshold, boolean value) {
        switch (threshold) {
            case 60: NotificationManager.SetPowersaveNotOnAt60Percent(Syscontext, value); break;
            case 50: NotificationManager.SetPowersaveNotOnAt50Percent(Syscontext, value); break;
            case 40: NotificationManager.SetPowersaveNotOnAt40Percent(Syscontext, value); break;
            case 30: NotificationManager.SetPowersaveNotOnAt30Percent(Syscontext, value); break;
            case 20: NotificationManager.SetPowersaveNotOnAt20Percent(Syscontext, value); break;
            case 10: NotificationManager.SetPowersaveNotOnAt10Percent(Syscontext, value); break;
        }
    }

    private void resetPSPercentFlags() {
        NotificationManager.SetPowersaveNotOnAt60Percent(Syscontext, false);
        NotificationManager.SetPowersaveNotOnAt50Percent(Syscontext, false);
        NotificationManager.SetPowersaveNotOnAt40Percent(Syscontext, false);
        NotificationManager.SetPowersaveNotOnAt30Percent(Syscontext, false);
        NotificationManager.SetPowersaveNotOnAt20Percent(Syscontext, false);
        NotificationManager.SetPowersaveNotOnAt10Percent(Syscontext, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void SendPSNotifTime () {
        if (isCharging || isPowerSaveOn) {
            if (isCharging) resetPSTimeFlags();
            return;
        }

        boolean notified = false;
        notified = checkPSTime(30, prefSwitchPowersave30Min, PSNotif30Min, M_POWER_SAVE_NOT_ON_30_MINUTES, notified);
        notified = checkPSTime(60, prefSwitchPowersave1Hour, PSNotif1Hour, M_POWER_SAVE_NOT_ON_1_HOURS, notified);
        notified = checkPSTime(90, prefSwitchPowersave15Hours, PSNotif15Hours, M_POWER_SAVE_NOT_ON_1_5_HOURS, notified);
        notified = checkPSTime(120, prefSwitchPowersave2Hours, PSNotif2Hours, M_POWER_SAVE_NOT_ON_2_HOURS, notified);
        notified = checkPSTime(150, prefSwitchPowersave25Hours, PSNotif25Hours, M_POWER_SAVE_NOT_ON_2_5_HOURS, notified);
        notified = checkPSTime(180, prefSwitchPowersave3Hours, PSNotif3Hours, M_POWER_SAVE_NOT_ON_3_HOURS, notified);
    }

    private boolean checkPSTime(int thresholdMin, boolean prefEnabled, boolean alreadyNotified, String message, boolean alreadySentThisCycle) {
        if (batTime <= thresholdMin && batTime > 0) {
            if (!alreadyNotified) {
                if (prefEnabled && !alreadySentThisCycle) {
                    new NotificationSender(Syscontext).send(TITLE_POWER_SAVE_IS_NOT_ON, message, 1);
                    alreadySentThisCycle = true;
                }
                setPSTimeFlag(thresholdMin, true);
            }
        } else {
            setPSTimeFlag(thresholdMin, false);
        }
        return alreadySentThisCycle;
    }

    private void setPSTimeFlag(int thresholdMin, boolean value) {
        switch (thresholdMin) {
            case 180: NotificationManager.SetPowersaveNotOn3HoursLeft(Syscontext, value); break;
            case 150: NotificationManager.SetPowersaveNotOn25HoursLeft(Syscontext, value); break;
            case 120: NotificationManager.SetPowersaveNotOn2HoursLeft(Syscontext, value); break;
            case 90: NotificationManager.SetPowersaveNotOn15HoursLeft(Syscontext, value); break;
            case 60: NotificationManager.SetPowersaveNotOn1HourLeft(Syscontext, value); break;
            case 30: NotificationManager.SetPowersaveNotOn30MinLeft(Syscontext, value); break;
        }
    }

    private void resetPSTimeFlags() {
        NotificationManager.SetPowersaveNotOn3HoursLeft(Syscontext, false);
        NotificationManager.SetPowersaveNotOn25HoursLeft(Syscontext, false);
        NotificationManager.SetPowersaveNotOn2HoursLeft(Syscontext, false);
        NotificationManager.SetPowersaveNotOn15HoursLeft(Syscontext, false);
        NotificationManager.SetPowersaveNotOn1HourLeft(Syscontext, false);
        NotificationManager.SetPowersaveNotOn30MinLeft(Syscontext, false);
    }

    public void SendPercentNotif () {
        if (isCharging) {
            resetPercentFlags();
            return;
        }

        boolean notified = false;
        notified = checkPercent(10, prefSwitch10percent, Notif10Percent, M_ON_10_PERCENT, notified);
        notified = checkPercent(20, prefSwitch20percent, Notif20Percent, M_ON_20_PERCENT, notified);
        notified = checkPercent(30, prefSwitch30percent, Notif30Percent, M_ON_30_PERCENT, notified);
        notified = checkPercent(40, prefSwitch40percent, Notif40Percent, M_ON_40_PERCENT, notified);
        notified = checkPercent(50, prefSwitch50percent, Notif50Percent, M_ON_50_PERCENT, notified);
        notified = checkPercent(60, prefSwitch60percent, Notif60Percent, M_ON_60_PERCENT, notified);
    }

    private boolean checkPercent(int threshold, boolean prefEnabled, boolean alreadyNotified, String message, boolean alreadySentThisCycle) {
        if (batPercent <= threshold) {
            if (!alreadyNotified) {
                if (prefEnabled && !alreadySentThisCycle) {
                    new NotificationSender(Syscontext).send(TITLE_POWER, message, 1);
                    alreadySentThisCycle = true;
                }
                setPercentFlag(threshold, true);
            }
        } else {
            setPercentFlag(threshold, false);
        }
        return alreadySentThisCycle;
    }

    private void setPercentFlag(int threshold, boolean value) {
        switch (threshold) {
            case 60: NotificationManager.SetPhoneHas60PercentLeft(Syscontext, value); break;
            case 50: NotificationManager.SetPhoneHas50PercentLeft(Syscontext, value); break;
            case 40: NotificationManager.SetPhoneHas40PercentLeft(Syscontext, value); break;
            case 30: NotificationManager.SetPhoneHas30PercentLeft(Syscontext, value); break;
            case 20: NotificationManager.SetPhoneHas20PercentLeft(Syscontext, value); break;
            case 10: NotificationManager.SetPhoneHas10PercentLeft(Syscontext, value); break;
        }
    }

    private void resetPercentFlags() {
        NotificationManager.SetPhoneHas60PercentLeft(Syscontext, false);
        NotificationManager.SetPhoneHas50PercentLeft(Syscontext, false);
        NotificationManager.SetPhoneHas40PercentLeft(Syscontext, false);
        NotificationManager.SetPhoneHas30PercentLeft(Syscontext, false);
        NotificationManager.SetPhoneHas20PercentLeft(Syscontext, false);
        NotificationManager.SetPhoneHas10PercentLeft(Syscontext, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void SendTimeNotif () {
        if (isCharging) {
            resetTimeFlags();
            return;
        }

        boolean notified = false;
        notified = checkTime(30, prefSwitch30Min, Notif30Min, M_ON_30_MINUTES, notified);
        notified = checkTime(60, prefSwitch1Hour, Notif1Hour, M_ON_1_HOURS, notified);
        notified = checkTime(90, prefSwitch15Hours, Notif15Hours, M_ON_1_5_HOURS, notified);
        notified = checkTime(120, prefSwitch2Hours, Notif2Hours, M_ON_2_HOURS, notified);
        notified = checkTime(150, prefSwitch25Hours, Notif25Hours, M_ON_2_5_HOURS, notified);
        notified = checkTime(180, prefSwitch3Hours, Notif3Hours, M_ON_3_HOURS, notified);
    }

    private boolean checkTime(int thresholdMin, boolean prefEnabled, boolean alreadyNotified, String message, boolean alreadySentThisCycle) {
        if (batTime <= thresholdMin && batTime > 0) {
            if (!alreadyNotified) {
                if (prefEnabled && !alreadySentThisCycle) {
                    new NotificationSender(Syscontext).send(TITLE_POWER, message, 1);
                    alreadySentThisCycle = true;
                }
                setTimeFlag(thresholdMin, true);
            }
        } else {
            setTimeFlag(thresholdMin, false);
        }
        return alreadySentThisCycle;
    }

    private void setTimeFlag(int thresholdMin, boolean value) {
        switch (thresholdMin) {
            case 180: NotificationManager.SetPhoneHas3HoursLeft(Syscontext, value); break;
            case 150: NotificationManager.SetPhoneHas25HoursLeft(Syscontext, value); break;
            case 120: NotificationManager.SetPhoneHas2HoursLeft(Syscontext, value); break;
            case 90: NotificationManager.SetPhoneHas15HoursLeft(Syscontext, value); break;
            case 60: NotificationManager.SetPhoneHas1HourLeft(Syscontext, value); break;
            case 30: NotificationManager.SetPhoneHas30MinLeft(Syscontext, value); break;
        }
    }

    private void resetTimeFlags() {
        NotificationManager.SetPhoneHas3HoursLeft(Syscontext, false);
        NotificationManager.SetPhoneHas25HoursLeft(Syscontext, false);
        NotificationManager.SetPhoneHas2HoursLeft(Syscontext, false);
        NotificationManager.SetPhoneHas15HoursLeft(Syscontext, false);
        NotificationManager.SetPhoneHas1HourLeft(Syscontext, false);
        NotificationManager.SetPhoneHas30MinLeft(Syscontext, false);
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    public void SendOtherNotif () {
        int randomInterval = (int)(Math.random() * 101);  // 0 to 100

        if (batPercent > 30 && randomInterval == 1 && prefSwitchTips) {
            NotificationSender notificationSender = new NotificationSender(Syscontext);
            notificationSender.send (TITLE_TIPS_AND_TRICKS, "Remember to check out some tips and tricks in the Battery Buddy app, to make your battery last longer!", 1);
        }
    }
}
