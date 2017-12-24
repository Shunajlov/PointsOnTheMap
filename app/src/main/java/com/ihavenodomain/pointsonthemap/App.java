package com.ihavenodomain.pointsonthemap;

import android.app.Application;
import android.content.SharedPreferences;

public class App extends Application {
    public static SharedPreferences SETTINGS;
    public static SharedPreferences.Editor EDITOR;

    @Override
    public void onCreate() {
        super.onCreate();

        SETTINGS = getSharedPreferences("settings", MODE_PRIVATE);
        EDITOR = SETTINGS.edit();
    }
}
