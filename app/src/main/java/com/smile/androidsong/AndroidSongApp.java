package com.smile.androidsong;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

public class AndroidSongApp extends Application {

    public static final int SingerOrdered = 1;
    public static final int NewSongOrdered = 2;
    public static final int HotSongOrdered = 3;
    public static final int LanguageOrdered = 4;
    public static Resources AppResources;
    public static Context AppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        AppResources = getResources();
        AppContext = getApplicationContext();
    }
}
