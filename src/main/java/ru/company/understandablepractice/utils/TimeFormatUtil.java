package ru.company.understandablepractice.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeFormatUtil {
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public static String formatTimeToString(LocalTime time){
        return time.format(timeFormatter);
    }
}
