package ru.company.understandablepractice.model.types.converters;

import jakarta.persistence.AttributeConverter;
import ru.company.understandablepractice.model.types.ApplicationFormStatus;

public class ApplicationFormStatusConverter implements AttributeConverter<ApplicationFormStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ApplicationFormStatus applicationFormStatus) {
        if(applicationFormStatus == null) return null;
        return applicationFormStatus.getKey();
    }

    @Override
    public ApplicationFormStatus convertToEntityAttribute(Integer key) {
        switch (key) {
            case 0 -> {
                return ApplicationFormStatus.NOT_CREATED;
            }
            case 1 -> {
                return ApplicationFormStatus.CREATED;
            }
            case 2 -> {
                return ApplicationFormStatus.PROCESSED;
            }
        }
        return null;
    }
}
