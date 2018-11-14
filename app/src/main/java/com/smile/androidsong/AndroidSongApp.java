package com.smile.androidsong;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.smile.smilepublicclasseslibrary.utilities.ScreenUtil;

public class AndroidSongApp extends Application {

    public static Resources AppResources;
    public static Context AppContext;
    public static float TextFontSize;

    @Override
    public void onCreate() {
        super.onCreate();

        AppResources = getResources();
        AppContext = getApplicationContext();

        boolean isTable = ScreenUtil.isTablet(this);
        if (isTable) {
            // not a cell phone, it is a tablet
            TextFontSize = 40;
            setTheme(R.style.AppThemeTextSizeLarge);
        } else {
            TextFontSize = 24;
            setTheme(R.style.AppThemeTextSizeSmall);
        }
    }
}
