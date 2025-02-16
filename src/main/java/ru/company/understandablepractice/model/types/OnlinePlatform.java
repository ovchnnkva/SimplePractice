package ru.company.understandablepractice.model.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OnlinePlatform {
    ZOOM("Zoom"),
    TELEMOST("Телемост"),
    TELEGRAM("Telegram"),
    WHATS_APP("WhatsApp"),
    SKYPE("Skype"),
    GOOGLE_MEET("GoogleMeet"),
    DISCORD("Discord");

    private final String tittle;
}
