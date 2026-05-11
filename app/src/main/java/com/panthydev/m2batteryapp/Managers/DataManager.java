package com.panthydev.m2batteryapp.Managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.panthydev.m2batteryapp.DataBase.DbHelper;
import com.panthydev.m2batteryapp.Interfaces.Callback;
import com.panthydev.m2batteryapp.data.DataObjects.App;
import com.panthydev.m2batteryapp.data.DataObjects.BatteryData;
import com.panthydev.m2batteryapp.data.DataObjects.DataPack;

public class DataManager
{
    /**
     * <p>Method to get battery data from the database.</p>
     * @param context Android context, just pass it in please
     * @param callback Method that should be called when fetching the data is done
     * @param hours How many hours back to get data from, must be positive.
     * @see DataPack DataPack class that contains the data
     * @see <a href="https://www.youtube.com/watch?v=dQw4w9WgXcQ"></a> see this link if you need help :3 (its the little arrow to the left)
     */
    public static void GetBatteryDataAsync(Context context, int hours, Callback<DataPack<BatteryData>> callback)
    {
        DbHelper dbHelper = new DbHelper(context);
        new Thread(new Runnable() {
            @Override
            public void run() {

                DataPack<BatteryData> dataPack = dbHelper.GetBatteryData(hours);
                callback.OnResult(dataPack);
            }
        }).start();
    }

    /**
     * <p>Method to send battery data to the database, in the form of a dataPack dto.</p>
     * @param context
     * @param dataPack the container holding an arraylist of battery data
     */
    public static void SetBatteryDataAsync(Context context, DataPack<BatteryData> dataPack){
        DbHelper dbHelper = new DbHelper(context);
        new Thread(new Runnable() {
            @Override
            public void run() {
                dbHelper.AddBatteryData(dataPack);
            }
        }).start();
    }

    public static void SetAppDataAsync(Context context, DataPack<App> dataPack){
        DbHelper dbHelper = new DbHelper(context);
        new Thread(new Runnable() {
            @Override
            public void run() {
                dbHelper.AddAppData(dataPack);
            }
        }).start();
    }


    /**
     * <p>Method to set the system discharge int into the shared preferences.</p>
     * @param context as always, please pass in the context or die
     * @param discharge the int to be saved
     */
    public static void SetSystemDischarge(Context context, int discharge){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        prefs.edit().putInt("system_discharge", discharge).apply();

    }

    /**
     * <p>Method to get the system discharge int from the shared preferences.</p>
     * @param context give it.... please
     * @return the discharge int, or 0 as a default value if the value doesnt exist
     */
    public static int GetSystemDischarge(Context context){
        SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);



        return prefs.getInt("system_discharge", 0);
    }













    //TODO implement GetUsageDataAsync() ... waiting for collection to be implemented

}
