package com.panthydev.m2batteryapp.DataBase;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.panthydev.m2batteryapp.Interfaces.Callback;
import com.panthydev.m2batteryapp.Managers.DataManager;
import com.panthydev.m2batteryapp.data.DataObjects.BatteryTestData;

public class TEST_Activity extends Activity
{
    float batteryPower;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        DataManager.GetBatteryDataAsync(getBaseContext(), new Callback<BatteryTestData>() {
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
