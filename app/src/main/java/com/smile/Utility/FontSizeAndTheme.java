package com.smile.Utility;

import android.content.Context;

import com.smile.androidsong.R;
import com.smile.smilepublicclasseslibrary.utilities.ScreenUtil;

public class FontSizeAndTheme {

    public static float GetTextFontSizeAndSetTheme(Context context) {

        float textFontSize;
        boolean isTable = ScreenUtil.isTablet(context);
        if (isTable) {
            // not a cell phone, it is a tablet
            textFontSize = 40;
            context.setTheme(R.style.AppThemeTextSizeLarge);
        } else {
            textFontSize = 24;
            context.setTheme(R.style.AppThemeTextSizeSmall);
        }

        return textFontSize;
    }
}
