package com.panthydev.m2batteryapp;

import android.app.Application;
import android.util.Log;

import com.panthydev.m2batteryapp.data.DataCollection.WorkHandler;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Ensure the data collection chain is bootstrapped and running whenever the app process starts.
        // This checks if any work is already scheduled; if not, it restarts the chain.
        try {
            WorkHandler.EnsureRunning(this);
        } catch (Exception e) {
            Log.e("App", "Failed to ensure data collection running", e);
        }
    }

}

