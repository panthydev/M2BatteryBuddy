package com.panthydev.m2batteryapp.Managers;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.panthydev.m2batteryapp.data.DataCollection.SystemDataCollector;

import java.time.Duration;
import java.util.Objects;

public class NotificationWorker {



    /// All methods accessable under here ///

    Context context;
    SystemDataCollector SysCollector = new SystemDataCollector(context);

    int batPercent = SysCollector.batLevelPercent;
    Duration batTime = SysCollector.remainingBatLife;
    boolean isPowerSaveOn = SysCollector.powerSaveOn;
    int batCurrentMAh = SysCollector.batCurrentMAh;

    // Get bools for which notification are turned on from prefs

    boolean PSNotif3Hours = NotificationManager.GetPowersaveNotOn3HoursLeft(context), PSNotif25Hours = NotificationManager.GetPowersaveNotOn25HoursLeft(context), PSNotif2Hours = NotificationManager.GetPowersaveNotOn2HoursLeft(context), PSNotif15Hours = NotificationManager.GetPowersaveNotOn15HoursLeft(context), PSNotif1Hour = NotificationManager.GetPowersaveNotOn1HourLeft(context), PSNotif30Min = NotificationManager.GetPowersaveNotOn30MinLeft(context);
    boolean PSNotif60Percent = NotificationManager.GetPowersaveNotOnAt60Percent(context), PSNotif50Percent = NotificationManager.GetPowersaveNotOnAt50Percent(context), PSNotif40Percent = NotificationManager.GetPowersaveNotOnAt40Percent(context), PSNotif30Percent = NotificationManager.GetPowersaveNotOnAt30Percent(context), PSNotif20Percent = NotificationManager.GetPowersaveNotOnAt20Percent(context), PSNotif10Percent = NotificationManager.GetPowersaveNotOnAt10Percent(context);

    void ChangeNotifBool(boolean NotifToChange) {
        if (!NotifToChange && batCurrentMAh <= 0) {
            NotifToChange = true;
        } else if (NotifToChange && batCurrentMAh >= 0) {
            NotifToChange = false;
        }
    }

    public void SendPSNotifPercent () {
        switch(batPercent) {
            case 60:
                TurnOnPowerSave ();
                ChangeNotifBool(PSNotif60Percent);
                NotificationManager.SetPowersaveNotOnAt60Percent(context, PSNotif60Percent);
                break;
            case 50:
                TurnOnPowerSave ();
                ChangeNotifBool(PSNotif50Percent);
                NotificationManager.SetPowersaveNotOnAt50Percent(context, PSNotif50Percent);
                break;
            case 40:
                TurnOnPowerSave ();
                ChangeNotifBool(PSNotif40Percent);
                NotificationManager.SetPowersaveNotOnAt40Percent(context, PSNotif40Percent);
                break;
            case 30:
                TurnOnPowerSave ();
                ChangeNotifBool(PSNotif30Percent);
                NotificationManager.SetPowersaveNotOnAt30Percent(context, PSNotif30Percent);
                break;
            case 20:
                TurnOnPowerSave ();
                ChangeNotifBool(PSNotif20Percent);
                NotificationManager.SetPowersaveNotOnAt20Percent(context, PSNotif20Percent);
                break;
            case 10:
                TurnOnPowerSave ();
                ChangeNotifBool(PSNotif10Percent);
                NotificationManager.SetPowersaveNotOnAt10Percent(context, PSNotif10Percent);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void SendPSNotifTime () {
        if (Objects.requireNonNull(batTime.toMinutes()) <= 180 && Objects.requireNonNull(batTime.toMinutes()) >= 160) {
            TurnOnPowerSave();
            ChangeNotifBool(PSNotif3Hours);
            NotificationManager.SetPowersaveNotOn3HoursLeft(context, PSNotif3Hours);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 150 && Objects.requireNonNull(batTime.toMinutes()) >= 130) {
            TurnOnPowerSave();
            ChangeNotifBool(PSNotif25Hours);
            NotificationManager.SetPowersaveNotOn25HoursLeft(context, PSNotif25Hours);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 120 && Objects.requireNonNull(batTime.toMinutes()) >= 100) {
            TurnOnPowerSave();
            ChangeNotifBool(PSNotif2Hours);
            NotificationManager.SetPowersaveNotOn2HoursLeft(context, PSNotif2Hours);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 90 && Objects.requireNonNull(batTime.toMinutes()) >= 70) {
            TurnOnPowerSave();
            ChangeNotifBool(PSNotif15Hours);
            NotificationManager.SetPowersaveNotOn15HoursLeft(context, PSNotif15Hours);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 60 && Objects.requireNonNull(batTime.toMinutes()) >= 40) {
            TurnOnPowerSave();
            ChangeNotifBool(PSNotif1Hour);
            NotificationManager.SetPowersaveNotOn1HourLeft(context, PSNotif1Hour);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 30 && Objects.requireNonNull(batTime.toMinutes()) >= 10) {
            TurnOnPowerSave();
            ChangeNotifBool(PSNotif30Min);
            NotificationManager.SetPowersaveNotOn30MinLeft(context, PSNotif30Min);
        }
    }

    void TurnOnPowerSave() {
        if (!isPowerSaveOn && batCurrentMAh <= 0) {
            // insert Jacobs notif method for hvilken besked de skal have
        }
        else {
            return;
        }
    }

    //////////////////////

    boolean Notif3Hours = NotificationManager.GetPhoneHas3HoursLeft(context), Notif25Hours = NotificationManager.GetPhoneHas25HoursLeft(context), Notif2Hours = NotificationManager.GetPhoneHas2HoursLeft(context), Notif15Hours = NotificationManager.GetPhoneHas15HoursLeft(context), Notif1Hour = NotificationManager.GetPhoneHas1HourLeft(context), Notif30Min = NotificationManager.GetPhoneHas30MinLeft(context);
    boolean Notif60Percent = NotificationManager.GetPhoneHas60PercentLeft(context), Notif50Percent = NotificationManager.GetPhoneHas50PercentLeft(context), Notif40Percent = NotificationManager.GetPhoneHas40PercentLeft(context), Notif30Percent = NotificationManager.GetPhoneHas30PercentLeft(context), Notif20Percent = NotificationManager.GetPhoneHas20PercentLeft(context), Notif10Percent = NotificationManager.GetPhoneHas10PercentLeft(context);


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void SendTimeNotif () {
        if (Objects.requireNonNull(batTime.toMinutes()) <= 180 && Objects.requireNonNull(batTime.toMinutes()) >= 160) {
            InformAboutTimeOrPercent();
            ChangeNotifBool(Notif3Hours);
            NotificationManager.SetPhoneHas3HoursLeft(context, Notif3Hours);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 150 && Objects.requireNonNull(batTime.toMinutes()) >= 130) {
            InformAboutTimeOrPercent();
            ChangeNotifBool(Notif25Hours);
            NotificationManager.SetPhoneHas25HoursLeft(context, Notif25Hours);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 120 && Objects.requireNonNull(batTime.toMinutes()) >= 100) {
            InformAboutTimeOrPercent();
            ChangeNotifBool(Notif2Hours);
            NotificationManager.SetPhoneHas2HoursLeft(context, Notif2Hours);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 90 && Objects.requireNonNull(batTime.toMinutes()) >= 70) {
            InformAboutTimeOrPercent();
            ChangeNotifBool(Notif15Hours);
            NotificationManager.SetPhoneHas15HoursLeft(context, Notif15Hours);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 60 && Objects.requireNonNull(batTime.toMinutes()) >= 40) {
            InformAboutTimeOrPercent();
            ChangeNotifBool(Notif1Hour);
            NotificationManager.SetPhoneHas1HourLeft(context, Notif1Hour);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 30 && Objects.requireNonNull(batTime.toMinutes()) >= 10) {
            InformAboutTimeOrPercent();
            ChangeNotifBool(Notif30Min);
            NotificationManager.SetPhoneHas30MinLeft(context, Notif30Min);
        }
    }

    public void SendPercentNotif () {
        // Here is the assumed bools which are saved in the prefs:
        // GetPowerSaveStatus().something

        switch(batPercent) {
            case 60:
                InformAboutTimeOrPercent();
                ChangeNotifBool(Notif60Percent);
                NotificationManager.SetPhoneHas60PercentLeft(context, Notif60Percent);
                break;
            case 50:
                InformAboutTimeOrPercent();
                ChangeNotifBool(Notif50Percent);
                NotificationManager.SetPhoneHas50PercentLeft(context, Notif50Percent);
                break;
            case 40:
                InformAboutTimeOrPercent();
                ChangeNotifBool(Notif40Percent);
                NotificationManager.SetPhoneHas40PercentLeft(context, Notif40Percent);
                break;
            case 30:
                InformAboutTimeOrPercent();
                ChangeNotifBool(Notif30Percent);
                NotificationManager.SetPhoneHas30PercentLeft(context, Notif30Percent);
                break;
            case 20:
                InformAboutTimeOrPercent();
                ChangeNotifBool(Notif20Percent);
                NotificationManager.SetPhoneHas20PercentLeft(context, Notif20Percent);
                break;
            case 10:
                InformAboutTimeOrPercent();
                ChangeNotifBool(Notif10Percent);
                NotificationManager.SetPhoneHas10PercentLeft(context, Notif10Percent);
                break;
        }
    }

    void InformAboutTimeOrPercent() {
        if (batCurrentMAh <= 0) {
            // insert Jacobs notif method
        }
        else {
            return;
        }
    }

    //////////////////////

    int randomInterval = (int)(Math.random() * 101);  // 0 to 100

    public void SendOtherNotif () {
        if (batPercent > 50 && randomInterval == 1) {
            // insert Jacobs notif method med en randomizer på hvilken notif de får
        }
    }
}
