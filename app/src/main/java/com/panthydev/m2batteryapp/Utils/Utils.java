package com.panthydev.m2batteryapp.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String GetStringDateTimeNow() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
