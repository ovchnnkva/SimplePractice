package ru.company.understandablepractice.model.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContactMethod {
    MESSENGER("Мессенджер"), SOCIAL_MEDIA("Социальная сеть"), AGGREGATOR("Агрегатор");

    private final String tittle;
}
