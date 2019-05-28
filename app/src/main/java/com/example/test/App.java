package com.example.test;

import android.app.Application;

/**
 * Created by BlingBling on 2019-05-28.
 */
public class App extends Application {

    public static App APP;

    @Override
    public void onCreate() {
        super.onCreate();
        APP = this;
    }
}
