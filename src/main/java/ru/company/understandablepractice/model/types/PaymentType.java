package ru.company.understandablepractice.model.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentType {
    CASHLESS("Безналичная"),
    CASH("Наличная");

    private final String title;
}
