package com.smile.androidsong;

import android.app.Application;

import com.smile.smilepublicclasseslibrary.utilities.ScreenUtil;

public class AndroidSongApp extends Application {

    public static float TextFontSize;

    @Override
    public void onCreate() {
        super.onCreate();
        boolean isTable = ScreenUtil.isTablet(this);
        if (isTable) {
            // not a cell phone, it is a tablet
            TextFontSize = 50;
            setTheme(R.style.AppThemeTextSize50);
        } else {
            TextFontSize = 30;
            setTheme(R.style.AppThemeTextSize30);
        }
    }
}
