package ru.company.understandablepractice.model.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClientType {
    CUSTOMER("Взрослый"),
    CHILD("Ребенок"),
    PAIR("Пара");

    private final String tittle;
}
