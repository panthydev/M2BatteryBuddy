package com.panthydev.m2batteryapp.DataBase;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.panthydev.m2batteryapp.Interfaces.Callback;
import com.panthydev.m2batteryapp.Managers.DataManager;
import com.panthydev.m2batteryapp.data.DataObjects.BatteryData;


public class TEST_Activity extends Activity
{
    float batteryPower;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        DataManager.GetBatteryDataAsync(getBaseContext(), new Callback<BatteryData>() {
            @Override
            public void OnResult(BatteryData Result) {
                SetBatteryPower(Result);
            }
        });
    }

    public void SetBatteryPower(BatteryData data)
    {
        batteryPower = data.percentLeft;;
        DoSomethingElse();
    }

    public void DoSomethingElse(){
        Log.d("TEST", "Doing something else");
    }



}
