package com.example.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    public static void logMessage(String format, Object... args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format(new Date());
        System.err.printf("[%s] %s%n", timestamp, String.format(format, args));
    }
}
