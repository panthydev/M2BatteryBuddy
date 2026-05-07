package com.panthydev.m2batteryapp.DataBase;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.panthydev.m2batteryapp.Interfaces.Callback;
import com.panthydev.m2batteryapp.Managers.DataManager;
import com.panthydev.m2batteryapp.data.DataObjects.BatteryData;
import com.panthydev.m2batteryapp.data.DataObjects.DataPack;


public class TEST_Activity extends Activity
{
    float batteryPower;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        DataManager.GetBatteryDataAsync(getBaseContext(), new Callback<DataPack<BatteryData>>() {
            @Override
            public void OnResult(DataPack<BatteryData> Result) {
                SetBatteryPower(Result);
            }
        });
    }

    public void SetBatteryPower(DataPack<BatteryData> data)
    {
        Log.d("TEST", "Datapack length: " + data.dataList.size());
        for (BatteryData batteryData : data.dataList) {
            batteryPower = batteryData.percentLeft;
            Log.d("TEST", "Battery Power: " + batteryPower);
        }
        DoSomethingElse();
    }

    public void DoSomethingElse(){
        Log.d("TEST", "Doing something else");
    }



}
