package com.panthydev.m2batteryapp.data.DataCollection;

import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
import static android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;
import static android.content.Context.BATTERY_SERVICE;
import static android.content.Context.POWER_SERVICE;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.Build;
import android.os.PowerManager;
import android.os.Process;
import android.util.Log;

import com.panthydev.m2batteryapp.Managers.DataManager;
import com.panthydev.m2batteryapp.Managers.NotificationManager;
import com.panthydev.m2batteryapp.Notifications.NotificationsMaker;
import com.panthydev.m2batteryapp.data.DataObjects.App;
import com.panthydev.m2batteryapp.data.DataObjects.BatteryData;
import com.panthydev.m2batteryapp.data.DataObjects.DataPack;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class SystemDataCollector{

    private static final String PREFS_NAME = "app_prefs";
    private static final String KEY_LAST_BATTERY_PERCENT = "last_battery_percent";
    private static final String KEY_LAST_CHARGE_COUNTER = "last_charge_counter";
    private static final int DISCHARGE_SCALE = 100000;

    App[] appArray;
    Context context;

    public int batLevelPercent;
    public int batCapMAh;
    public int batCurrentMAh;
    public boolean powerSaveOn;
    public int remainingBatLife;
    public boolean isCharging;

    public SystemDataCollector(Context context){
        this.context = context;
    }

    public void CollectAndSendBatteryDataToDB() { //Needs to run on the interval
        BatteryManager BM = (BatteryManager) context.getSystemService(BATTERY_SERVICE); //Getting access to the Battery Manager
        PowerManager PM = (PowerManager) context.getSystemService(POWER_SERVICE); //Getting access to the Power Manager

        batLevelPercent = BM.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY); //Finds Battery percentage at this moment
        batCapMAh = BM.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER); //Entire Battery capacity in microampere-hours, as an integer.
        batCurrentMAh = BM.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW); //Instantaneous battery current in microamperes, as an integer (positive means it is charging, negative is draining)
        powerSaveOn = PM.isPowerSaveMode(); //Checks if powersaving mode is on
        isCharging = BM.isCharging();

        //Checks if the phone which runs this, has a high enough API level (is new enough)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            try {
                // Use reflection to avoid potential signature mismatches with desugared java.time types on some platform versions
                 remainingBatLife = PM.getBatteryDischargePrediction(); //Estimates the time in minutes there is left on the phone
                if (remainingBatLife != null) {
                    System.out.println("Estimated remaining battery left is: " + (remainingBatLife.toMinutes()) + " minutes");
                }
            } catch (Throwable t) {
                // Method might not be present on this specific device implementation even if API >= 31, or other reflection error
                System.out.println("Could not get battery discharge prediction: " + t.getMessage());
                //TODO implement all the other stuff to let user know it doesnt work + add to prefs
            }
        }

        NotificationManager.SetBatteryPercent(context, batLevelPercent);
        NotificationManager.SetEstBatTime(context, (int) remainingBatLife.toSeconds());
        NotificationManager.SetIsCharging(context, isCharging);
        NotificationManager.SetPowerSaveOn(context, powerSaveOn);

        BatteryData batData = new BatteryData(batLevelPercent, batCapMAh, remainingBatLife, batCapMAh, powerSaveOn); //Making a battery data object with the collected data
        var datapack = new DataPack<BatteryData>();
        datapack.AddData(batData); //Adding the battery data object to the data pack
        DataManager.SetBatteryDataAsync(context,datapack) ; //Sending the battery data object to the database
    }

    /**
     * should only run once when the app is first opened
     */
    public void CollectAndSendUsageDataToDB () { // Needs to run once when the app is first opened
        List<ApplicationInfo> applicationInfoList = context.getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA); // Get a list of packages with applications
        int backgroundProcessCount = getBackgroundProcessCount();

        appArray = new App[applicationInfoList.size()]; // Making an array for all the app objects ToBeMade
        int i = 0; // just a Counter, dont worry :)

        for (ApplicationInfo applicationInfo : applicationInfoList) {
            String appName = applicationInfo.packageName; // Finding the app name
            int appCat = -1; // CATEGORY_UNDEFINED
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                appCat = applicationInfo.category; // Finding the app category
            }
            int appDischarge = 0; // No discharge data collected yet

            if (appCat != -1) { //Only makes objects of importance, based on the category number (aka all but -1)
                appArray[i] = new App(appName, appCat, appDischarge, backgroundProcessCount); // Making all the app objects
                System.out.println("here is the app: " + appArray[i].getAppName() + " The Category: " + appArray[i].getAppCategory());
            }
            i++;
        }
        var datapack = new DataPack<App>();

        for (App app : appArray) {
            if (app != null) {
                datapack.AddData(app); // Adding the app data object to the data pack
            }
        }
        DataManager.SetAppDataAsync(context,datapack) ; //Sending the app data

        //note from panthy: why have they not used enums, are they weird ?

        // When it gathers categories, an int is assigned, so here is every int and their respective category:
        // -1 = CATEGORY_UNDEFINED (Value when category is undefined.)
        // 0 = CATEGORY_GAME (Category for apps which are primarily games.)
        // 1 = CATEGORY_AUDIO (Category for apps which primarily work with audio or music, such as music players.)
        // 2 = CATEGORY_VIDEO (Category for apps which primarily work with video or movies, such as streaming video apps.)
        // 3 = CATEGORY_IMAGE (Category for apps which primarily work with images or photos, such as camera or gallery apps.)
        // 4 = CATEGORY_SOCIAL (Category for apps which are primarily social apps, such as messaging, communication, email, or social network apps.)
        // 5 = CATEGORY_NEWS (Category for apps which are primarily news apps, such as newspapers, magazines, or sports apps.)
        // 6 = CATEGORY_MAPS (Category for apps which are primarily maps apps, such as navigation apps.)
        // 7 = CATEGORY_PRODUCTIVITY (Category for apps which are primarily productivity apps, such as cloud storage or workplace apps.)
        // 8 = CATEGORY_ACCESSIBILITY (Category for apps which are primarily accessibility apps, such as screen-readers.)

    }

    int appDischarge; // The power discharge of an app used (with the system discharged)
    int SystemDischarge; // The power discharge of the system

    public void appDischargeTimer () { //Needs to run on the interval
        BatteryManager BM = (BatteryManager) context.getSystemService(BATTERY_SERVICE); //Getting access to the Battery Manager (again lol)
        if (BM == null) {
            setSystemDischarge(0);
            return;
        }

        int currentBatteryPercent = BM.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        int lastBatteryPercent = getLastBatteryPercent();

        // Prefer using the charge counter (more precise than percent). It's reported in microampere-hours.
        int currentChargeCounter = BM.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
        int lastChargeCounter = getLastChargeCounter();

        // We'll compute a human-friendly discharge in milliamp-hours (mAh).
        // Store as uAh in DB for precision; display as mAh or uAh when logging.
        int dischargeValue_uAh = 0;

        if (lastChargeCounter >= 0 && currentChargeCounter >= 0) {
            long deltaUAh = (long) lastChargeCounter - (long) currentChargeCounter; // microampere-hours drop
            if (deltaUAh > 0) {
                dischargeValue_uAh = (int) deltaUAh;
                Log.d("SystemDataCollector", "Using charge-counter delta: " + formatDischarge(dischargeValue_uAh) + " (raw: " + dischargeValue_uAh + " uAh)");
            }
        }

        // If charge-counter didn't produce a positive delta, fall back to percent-based delta (coarse)
        if (dischargeValue_uAh == 0) {
            int percentDrop = calculatePercentDrop(lastBatteryPercent, currentBatteryPercent);
            if (percentDrop > 0) {
                // try to estimate full capacity using available charge-counter reading and percent
                long estimatedFull_uAh = -1;
                if (currentChargeCounter > 0 && currentBatteryPercent > 0) {
                    estimatedFull_uAh = (long) currentChargeCounter * 100L / (long) currentBatteryPercent;
                } else if (lastChargeCounter > 0 && lastBatteryPercent > 0) {
                    estimatedFull_uAh = (long) lastChargeCounter * 100L / (long) lastBatteryPercent;
                }

                if (estimatedFull_uAh > 0) {
                    long deltaUAhEstimate = (long) percentDrop * estimatedFull_uAh / 100L;
                    dischargeValue_uAh = (int) deltaUAhEstimate;
                    Log.d("SystemDataCollector", "Estimated from percent: " + percentDrop + "% -> approx " + formatDischarge(dischargeValue_uAh) + " (raw: " + dischargeValue_uAh + " uAh)");
                } else {
                    // last-resort heuristic: assume ~30 mAh per percent (coarse)
                    dischargeValue_uAh = percentDrop * 30000; // 30 mAh per percent = 30000 uAh per percent
                    Log.d("SystemDataCollector", "Fallback percent heuristic: " + percentDrop + "% -> " + formatDischarge(dischargeValue_uAh) + " (raw: " + dischargeValue_uAh + " uAh)");
                }
            }
        }

        persistLastBatteryPercent(currentBatteryPercent);
        persistLastChargeCounter(currentChargeCounter);

        // Determine the current foreground package. Prefer UsageStatsManager (more reliable when available),
        // fall back to scanning running app processes.
        String foregroundPackage = detectForegroundPackage();
        Log.d("SystemDataCollector", "Detected foregroundPackage=" + foregroundPackage + " discharge=" + formatDischarge(dischargeValue_uAh) + " currentBatteryPercent=" + currentBatteryPercent + " lastBatteryPercent=" + lastBatteryPercent);

        if (lastBatteryPercent < 0) {
            // First sample after install/restart: keep the baseline for next tick.
            return;
        }

        if (foregroundPackage != null) {
            setAppDischarge(dischargeValue_uAh);
            dischargeSaver(foregroundPackage);
        } else {
            setSystemDischarge(dischargeValue_uAh);
        }
    }

    public void setAppDischarge (int appDischarge) {
        this.appDischarge = appDischarge;
    }

    public int getAppDischarge () {
        return appDischarge;
    }

    public void setSystemDischarge (int SystemDischarge) {
        this.SystemDischarge = SystemDischarge;
    }

    /**
     * Record a single app discharge datapoint for the given foreground package.
     * We only create a DataPack containing that one app to be inserted/updated in DB.
     */
    public void dischargeSaver (String foregroundPackage) {
        if (foregroundPackage == null || foregroundPackage.isEmpty()) return;

        // normalize again (safety)
        if (foregroundPackage.contains(":")) foregroundPackage = foregroundPackage.split(":", 2)[0];

        List<ApplicationInfo> applicationInfoList = context.getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);

        int appCat = -1; // default
        for (ApplicationInfo applicationInfo : applicationInfoList) {
            if (applicationInfo == null || applicationInfo.packageName == null) continue;
            if (!applicationInfo.packageName.equals(foregroundPackage)) continue;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                appCat = applicationInfo.category;
            }
            break;
        }

        int appDischarge = getAppDischarge();
        int backgroundProcessCount = getBackgroundProcessCount();
        App app = new App(foregroundPackage, appCat, appDischarge, backgroundProcessCount);
        var datapack = new DataPack<App>();
        datapack.AddData(app);
        DataManager.SetAppDataAsync(context, datapack);
    }

    /**
     * Try to detect the foreground package. First use UsageStatsManager by querying recent events and
     * returning the last MOVE_TO_FOREGROUND event. If that fails (no permission or no events), fall back
     * to scanning running app processes for IMPORTANCE_FOREGROUND.
     */
    private String detectForegroundPackage() {
        String pkg = null;
        try {
            UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            if (usm != null) {
                long now = System.currentTimeMillis();
                UsageEvents events = usm.queryEvents(now - 5 * 60 * 1000, now);
                UsageEvents.Event e = new UsageEvents.Event();
                String lastPkg = null;
                while (events.hasNextEvent()) {
                    events.getNextEvent(e);
                    if (e.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                        lastPkg = e.getPackageName();
                    }
                }
                if (lastPkg != null && !lastPkg.isEmpty()) {
                    pkg = lastPkg;
                }
            }
        } catch (Exception ex) {
            // ignore and fall back
        }

        if (pkg != null) return normalizePackage(pkg);

        // fallback: scan running processes
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            try {
                for (ActivityManager.RunningAppProcessInfo proc : am.getRunningAppProcesses()) {
                    if (proc == null) continue;
                    if (proc.importance == IMPORTANCE_FOREGROUND || proc.importance == IMPORTANCE_VISIBLE) {
                        String name = proc.processName;
                        if (name == null) continue;
                        name = normalizePackage(name);
                        if (!name.equals(context.getPackageName())) return name;
                        if (pkg == null) pkg = name;
                    }
                }
            } catch (Exception ex) {
                // ignore
            }
        }

        return pkg == null ? null : normalizePackage(pkg);
    }

    private String normalizePackage(String name) {
        if (name == null) return null;
        if (name.contains(":")) return name.split(":", 2)[0];
        return name;
    }

    /**
     * Coarse count of background stuff running right now.
     * On modern Android, ActivityManager.getRunningAppProcesses() is highly restricted.
     * We use UsageStatsManager (requires "Usage Access" permission) to estimate active background processes.
     */
    private int getBackgroundProcessCount() {
        if (!hasUsageStatsPermission()) {
            Log.w("SystemDataCollector", "Usage Access permission (PACKAGE_USAGE_STATS) NOT granted. Background process count will likely be 0. Please grant it in Settings > Security > Special App Access > Usage Access.");
        }

        Set<String> activePackages = new HashSet<>();
        String myPkg = context.getPackageName();

        UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        if (usm != null) {
            try {
                long now = System.currentTimeMillis();
                // 1. Check UsageStats for recent activity (last 30 minutes)
                // We use INTERVAL_BEST to let the system decide the best resolution.
                List<UsageStats> stats = usm.queryUsageStats(UsageStatsManager.INTERVAL_BEST, now - 30 * 60 * 1000, now);
                if (stats != null) {
                    for (UsageStats s : stats) {
                        // If it was used in the last 15 minutes, consider it "active"
                        if (s.getLastTimeUsed() > now - 15 * 60 * 1000) {
                            String pkg = s.getPackageName();
                            if (pkg != null && !pkg.equals(myPkg)) {
                                activePackages.add(normalizePackage(pkg));
                            }
                        }
                    }
                }

                // 2. Check UsageEvents for state changes in the last 15 minutes
                UsageEvents events = usm.queryEvents(now - 15 * 60 * 1000, now);
                UsageEvents.Event e = new UsageEvents.Event();
                while (events.hasNextEvent()) {
                    events.getNextEvent(e);
                    String pkg = e.getPackageName();
                    if (pkg != null && !pkg.equals(myPkg)) {
                        activePackages.add(normalizePackage(pkg));
                    }
                }
            } catch (Exception ex) {
                Log.e("SystemDataCollector", "UsageStatsManager error: " + ex.getMessage());
            }
        }

        // Subtract the current foreground app if detected
        String fg = detectForegroundPackage();
        if (fg != null) {
            activePackages.remove(normalizePackage(fg));
        }

        int count = activePackages.size();
        Log.d("SystemDataCollector", "getBackgroundProcessCount: " + count + " active packages (excluding foreground). Permission: " + hasUsageStatsPermission());
        
        // Fallback: If UsageStats returns nothing (e.g. permission just granted but no events yet, or old device)
        if (count == 0) {
            try {
                ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                if (am != null) {
                    List<ActivityManager.RunningAppProcessInfo> processes = am.getRunningAppProcesses();
                    if (processes != null) {
                        for (ActivityManager.RunningAppProcessInfo proc : processes) {
                            if (proc != null && proc.processName != null && !proc.processName.equals(myPkg)) {
                                if (proc.importance > IMPORTANCE_VISIBLE) {
                                    count++;
                                }
                            }
                        }
                    }
                }
            } catch (Exception ignored) {}
        }

        return count;
    }

    private boolean hasUsageStatsPermission() {
        try {
            AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            if (appOps == null) return false;
            int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, 
                    android.os.Process.myUid(), context.getPackageName());
            return mode == AppOpsManager.MODE_ALLOWED;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Format discharge value (stored as uAh in DB) to human-friendly string for logging.
     * Shows as mAh if >= 1000 uAh, otherwise as uAh.
     * Example: 8740 uAh -> "8.74 mAh", 500 uAh -> "500 uAh"
     */
    private String formatDischarge(int discharge_uAh) {
        if (discharge_uAh >= 1000) {
            double mAh = discharge_uAh / 1000.0;
            return String.format(Locale.US, "%.2f mAh", mAh);
        } else {
            return discharge_uAh + " uAh";
        }
    }

    private int calculatePercentDrop(int lastBatteryPercent, int currentBatteryPercent) {
        if (lastBatteryPercent < 0) return 0;
        return Math.max(lastBatteryPercent - currentBatteryPercent, 0);
    }

    private int getLastBatteryPercent() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_LAST_BATTERY_PERCENT, -1);
    }

    private void persistLastBatteryPercent(int batteryPercent) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putInt(KEY_LAST_BATTERY_PERCENT, batteryPercent).apply();
    }

    private int getLastChargeCounter() {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_LAST_CHARGE_COUNTER, -1);
    }

    private void persistLastChargeCounter(int chargeCounter) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putInt(KEY_LAST_CHARGE_COUNTER, chargeCounter).apply();
    }
}
