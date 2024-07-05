package ru.company.understandablepractice.model.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BringsClient {
    PARENT("Родитель"), BOTH_PARENTS("Оба родителя"), TRUSTEE("Попечитель");

    private final String title;
}
