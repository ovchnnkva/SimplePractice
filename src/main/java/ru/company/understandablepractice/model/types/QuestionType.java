package ru.company.understandablepractice.model.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QuestionType {
    MANY("Несколько из списка"),
    ONE("Один из списка"),
    DETAILED("Развернутый ответ");
    private final String key;
}
