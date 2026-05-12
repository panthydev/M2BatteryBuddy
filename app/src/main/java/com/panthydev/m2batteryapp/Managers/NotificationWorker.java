package com.panthydev.m2batteryapp.Managers;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.panthydev.m2batteryapp.data.DataCollection.SystemDataCollector;

import java.time.Duration;
import java.util.Objects;

public class NotificationWorker {

    Context context;
    SystemDataCollector SysCollector = new SystemDataCollector(context);

    int batPercent = SysCollector.batLevelPercent;
    Duration batTime = SysCollector.remainingBatLife;
    boolean isPowerSaveOn = SysCollector.powerSaveOn;
    int batCurrentMAh = SysCollector.batCurrentMAh;

    // Get bools for which notification are turned on from prefs

    boolean PSNotif3Hours = false, PSNotif25Hours = false, PSNotif2Hours = false, PSNotif15Hours = false, PSNotif1Hour = false, PSNotif30Min = false;
    boolean PSNotif60Percent = false, PSNotif50Percent = false, PSNotif40Percent = false, PSNotif30Percent = false, PSNotif20Percent = false, PSNotif10Percent = false;


    void ChangeNotifBool(boolean NotifToChange) {
        if (!NotifToChange && batCurrentMAh <= 0) {
            NotifToChange = true;
        } else if (NotifToChange && batCurrentMAh >= 0) {
            NotifToChange = false;
        }
    }

    public void SendPSNotifPercent () {
        // Here is the assumed bools which are saved in the prefs:
        // GetPowerSaveStatus().something

        switch(batPercent) {
            case 60:
                TurnOnPowerSave ();
                ChangeNotifBool(PSNotif60Percent);
                break;
            case 50:
                TurnOnPowerSave ();
                ChangeNotifBool(PSNotif50Percent);
                break;
            case 40:
                TurnOnPowerSave ();
                ChangeNotifBool(PSNotif40Percent);
                break;
            case 30:
                TurnOnPowerSave ();
                ChangeNotifBool(PSNotif30Percent);
                break;
            case 20:
                TurnOnPowerSave ();
                ChangeNotifBool(PSNotif20Percent);
                break;
            case 10:
                TurnOnPowerSave ();
                ChangeNotifBool(PSNotif10Percent);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void SendPSNotifTime () {
        // Here is the assumed bools which are saved in the prefs:
        // GetPowerSaveStatus().something

        if (Objects.requireNonNull(batTime.toMinutes()) <= 180 && Objects.requireNonNull(batTime.toMinutes()) >= 160) {
            TurnOnPowerSave();
            ChangeNotifBool(PSNotif3Hours);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 150 && Objects.requireNonNull(batTime.toMinutes()) >= 130) {
            TurnOnPowerSave();
            ChangeNotifBool(PSNotif25Hours);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 120 && Objects.requireNonNull(batTime.toMinutes()) >= 100) {
            TurnOnPowerSave();
            ChangeNotifBool(PSNotif2Hours);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 90 && Objects.requireNonNull(batTime.toMinutes()) >= 70) {
            TurnOnPowerSave();
            ChangeNotifBool(PSNotif15Hours);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 60 && Objects.requireNonNull(batTime.toMinutes()) >= 40) {
            TurnOnPowerSave();
            ChangeNotifBool(PSNotif1Hour);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 30 && Objects.requireNonNull(batTime.toMinutes()) >= 10) {
            TurnOnPowerSave();
            ChangeNotifBool(PSNotif30Min);
        }
    }

    void TurnOnPowerSave() {
        if (!isPowerSaveOn && batCurrentMAh <= 0) {
            // insert Jacobs notif method
        }
        else {
            return;
        }
    }

    //////////////////////

    boolean Notif3Hours = false, Notif25Hours = false, Notif2Hours = false, Notif15Hours = false, Notif1Hour = false, Notif30Min = false;
    boolean Notif60Percent = false, Notif50Percent = false, Notif40Percent = false, Notif30Percent = false, Notif20Percent = false, Notif10Percent = false;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void SendTimeNotif () {
        // Here is the assumed bools which are saved in the prefs:
        // GetPowerSaveStatus().something

        if (Objects.requireNonNull(batTime.toMinutes()) <= 180 && Objects.requireNonNull(batTime.toMinutes()) >= 160) {
            InformAboutTimeOrPercent();
            ChangeNotifBool(Notif3Hours);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 150 && Objects.requireNonNull(batTime.toMinutes()) >= 130) {
            InformAboutTimeOrPercent();
            ChangeNotifBool(Notif25Hours);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 120 && Objects.requireNonNull(batTime.toMinutes()) >= 100) {
            InformAboutTimeOrPercent();
            ChangeNotifBool(Notif2Hours);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 90 && Objects.requireNonNull(batTime.toMinutes()) >= 70) {
            InformAboutTimeOrPercent();
            ChangeNotifBool(Notif15Hours);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 60 && Objects.requireNonNull(batTime.toMinutes()) >= 40) {
            InformAboutTimeOrPercent();
            ChangeNotifBool(Notif1Hour);
        } else if (Objects.requireNonNull(batTime.toMinutes()) <= 30 && Objects.requireNonNull(batTime.toMinutes()) >= 10) {
            InformAboutTimeOrPercent();
            ChangeNotifBool(Notif30Min);
        }
    }

    public void SendPercentNotif () {
        // Here is the assumed bools which are saved in the prefs:
        // GetPowerSaveStatus().something

        switch(batPercent) {
            case 60:
                InformAboutTimeOrPercent();
                ChangeNotifBool(Notif60Percent);
                break;
            case 50:
                InformAboutTimeOrPercent();
                ChangeNotifBool(Notif50Percent);
                break;
            case 40:
                InformAboutTimeOrPercent();
                ChangeNotifBool(Notif40Percent);
                break;
            case 30:
                InformAboutTimeOrPercent();
                ChangeNotifBool(Notif30Percent);
                break;
            case 20:
                InformAboutTimeOrPercent();
                ChangeNotifBool(Notif20Percent);
                break;
            case 10:
                InformAboutTimeOrPercent();
                ChangeNotifBool(Notif10Percent);
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
