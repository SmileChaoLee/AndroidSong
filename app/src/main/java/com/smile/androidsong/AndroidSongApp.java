package com.smile.androidsong;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.smile.smilelibraries.utilities.ScreenUtil;

public class AndroidSongApp extends Application {

    public static final int SingerOrdered = 1;
    public static final int NewSongOrdered = 2;
    public static final int NewSongLanguageOrdered = 21;
    public static final int HotSongOrdered = 3;
    public static final int HotSongLanguageOrdered = 31;
    public static final int LanguageOrdered = 4;
    public static final int LanguageWordsOrdered = 41;
    public static final int FontSize_Scale_Type = ScreenUtil.FontSize_Pixel_Type;

    public static Resources AppResources;
    public static Context AppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        AppResources = getResources();
        AppContext = getApplicationContext();
    }
}
