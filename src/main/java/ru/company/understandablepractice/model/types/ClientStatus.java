package ru.company.understandablepractice.model.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ClientStatus {
    REGULAR("Регулярный"),
    ON_REQUEST("По запросу"),
    COMPLETED("Завершен"),
    REQUEST("Заявка");
    private final String tittle;
}
