package ru.company.understandablepractice.dto.converter;

import org.springframework.stereotype.Service;

@Service
public class YesNoConverter {
    public boolean stringToBoolean(String value) {
        return value.equals("Да");
    }

    public String booleanToString(boolean value) {
        return value ? "Да" : "Нет";
    }
}
