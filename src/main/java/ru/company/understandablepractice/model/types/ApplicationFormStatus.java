package ru.company.understandablepractice.model.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicationFormStatus {
    NOT_CREATED(0),
    CREATED(1),
    PROCESSED(2),
    INVALID(3);
    private final int key;
}
