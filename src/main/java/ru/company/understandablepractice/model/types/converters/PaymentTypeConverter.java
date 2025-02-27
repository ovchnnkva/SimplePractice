package ru.company.understandablepractice.model.types.converters;

import jakarta.persistence.AttributeConverter;
import ru.company.understandablepractice.model.types.PaymentType;

public class PaymentTypeConverter implements AttributeConverter<PaymentType, String> {
    @Override
    public String convertToDatabaseColumn(PaymentType paymentType) {
        if (paymentType == null) return null;
        return paymentType.getTitle();
    }

    @Override
    public PaymentType convertToEntityAttribute(String s) {
        if (s == null) return null;
        switch (s) {
            case "Безналичная" -> {
                return PaymentType.CASHLESS;
            }
            case "Наличная" -> {
                return PaymentType.CASH;
            }
            default -> throw new IllegalArgumentException(s + " not supported.");
        }
    }
}
