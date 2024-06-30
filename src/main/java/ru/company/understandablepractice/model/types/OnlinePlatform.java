package ru.company.understandablepractice.model.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OnlinePlatform {
    ZOOM("Zoom"),
    TELEMOST("Телемост"),
    GOOGLE_MEET("GoogleMeet"),
    TELEGRAM("Telegram"),
    WHATS_APP("WhatsApp"),
    SKYPE("Skype");

    private final String tittle;
}
