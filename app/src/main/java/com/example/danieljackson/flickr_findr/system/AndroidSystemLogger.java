package com.example.danieljackson.flickr_findr.system;

import android.util.Log;

public class AndroidSystemLogger implements SystemLogger {
    @Override
    public void d(String tag, String message) {
        Log.d(tag, message);
    }

    @Override
    public void e(String tag, String message) {
        Log.e(tag, message);
    }

    @Override
    public void e(String tag, String message, Throwable throwable) {
        Log.e(tag, message, throwable);
    }

    @Override
    public void i(String tag, String message) {
        Log.i(tag, message);
    }

}
