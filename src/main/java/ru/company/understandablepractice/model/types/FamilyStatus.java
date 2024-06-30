package ru.company.understandablepractice.model.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum FamilyStatus {
    MARRIED("Женат/Замужем"),
    UNMARRIED("Холост/Незамужем"),
    DIVORCED("В разводе"),
    WIDOWER("Вдовец/Вдова"),
    FAILED_MARRIAGE("Сожительство/Несостоявшийся брак"),
    MARRIAGE_WITH_SEPARATION("Официальный брак с раздельным проживанием"),
    LONELINESS("Одиночество/Не в постоянных отношениях"),
    FAMILIES_WITH_CHILDREN("Семьи с детьми");

    private final String title;
}
