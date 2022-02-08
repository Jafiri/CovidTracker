package com.example.coronatracker;

import android.app.Application;

import com.onesignal.OneSignal;

public class MyApplication extends Application {

    private static final String ONESIGNAL_APP_ID = "48f6660f-830c-4fa9-bac9-c306a2252e9b";

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
    }
}
