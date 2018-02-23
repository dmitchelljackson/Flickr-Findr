package com.example.danieljackson.flickr_findr.system;

public interface SystemLogger {
    void d(String tag, String message);

    void e(String tag, String message);

    void e(String tag,String message, Throwable throwable);

    void i(String tag, String message);

}
