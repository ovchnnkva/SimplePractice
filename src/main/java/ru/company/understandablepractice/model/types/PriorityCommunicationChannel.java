package ru.company.understandablepractice.model.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PriorityCommunicationChannel {
    TELEGRAM("Telegram"),
    WHATS_APP("WhatsApp"),
    VIBER("Viber"),
    SKYPE("Skype"),
    MAIL("Почта"),
    SNAPCHAT("Snapchat");

    private final String tittle;
}
