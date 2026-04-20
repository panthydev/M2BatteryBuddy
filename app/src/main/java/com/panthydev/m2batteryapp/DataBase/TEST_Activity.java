package com.panthydev.m2batteryapp.DataBase;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.panthydev.m2batteryapp.Interfaces.Callback;
import com.panthydev.m2batteryapp.Managers.DataManager;
import com.panthydev.m2batteryapp.data.DataObjects.BatteryTestData;

public class TEST_Activity extends Activity
{
    int batteryPower;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        DataManager.GetBatteryDataAsync(new Callback<BatteryTestData>() {
            @Override
            public void OnResult(BatteryTestData Result) {
                SetBatteryPower(Result);
            }
        });
    }

    public void SetBatteryPower(BatteryTestData data)
    {
        batteryPower = data.BatteryPowerLeft;
        DoSomethingElse();
    }

    public void DoSomethingElse(){
        Log.d("TEST", "Doing something else");
    }



}
