package com.ihavenodomain.pointsonthemap;

import android.app.Application;
import android.content.SharedPreferences;

public class App extends Application {
    public static SharedPreferences SETTINGS;
    public static SharedPreferences.Editor EDITOR;

    private static boolean activityVisible;

    @Override
    public void onCreate() {
        super.onCreate();

        SETTINGS = getSharedPreferences("settings", MODE_PRIVATE);
        EDITOR = SETTINGS.edit();
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

}
