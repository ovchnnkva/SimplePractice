package ru.company.understandablepractice.model.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClientType {
    ADULT(1),
    CHILD(2),
    PAIR(3);

    private final int key;
}
