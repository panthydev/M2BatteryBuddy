package com.panthydev.m2batteryapp.Utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.panthydev.m2batteryapp.data.DataObjects.BatteryData;

import java.time.Duration;
import java.util.Date;

/**
 * This class is a static factory that can create objects for testing purposes
 */
public class TestingFactory {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static BatteryData CreateTestingBatteryData(){
        return new BatteryData(50, 2000, Duration.ofSeconds(200), 10000, false, new Date());
    }
}
