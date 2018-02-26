package com.example.danieljackson.flickr_findr;

import com.example.danieljackson.flickr_findr.system.SystemLogger;

public class JavaSystemLogger implements SystemLogger {

    @Override
    public void d(String tag, String message) {
        System.out.println(tag + " : " + message);
    }

    @Override
    public void e(String tag, String message) {
        System.out.println(tag + " : " + message);
    }

    @Override
    public void e(String tag, String message, Throwable throwable) {
        System.out.println(tag + " : " + message + "\n" + throwable);
    }

    @Override
    public void i(String tag, String message) {
        System.out.println(tag + " : " + message);
    }
}
