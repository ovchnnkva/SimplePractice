package ru.company.understandablepractice.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static String formatDateToString(LocalDate date) {
        return date.format(formatter);
    }
}
