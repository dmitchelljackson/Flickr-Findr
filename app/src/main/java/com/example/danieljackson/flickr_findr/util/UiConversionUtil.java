package com.example.danieljackson.flickr_findr.util;

import android.content.res.Resources;

public class UiConversionUtil {

    public static int convertPxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }
}
