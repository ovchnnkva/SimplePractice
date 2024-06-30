package ru.company.understandablepractice.model.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MeetingFormat {
    ONLINE("Онлайн"),
    OFFLINE("Офлайн");

    private final String tittle;
}
