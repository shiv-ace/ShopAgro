package com.codeshiv.shopagro;

import android.app.Application;

import com.facebook.appevents.AppEventsLogger;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class ShopAgroApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppEventsLogger.activateApp(this);
    }
}
