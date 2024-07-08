package ru.company.understandablepractice.model.types.converters;

import jakarta.persistence.AttributeConverter;
import ru.company.understandablepractice.model.types.FamilyStatus;

public class FamilyStatusConverter implements AttributeConverter<FamilyStatus, String> {

    @Override
    public String convertToDatabaseColumn(FamilyStatus familyStatus) {
        if(familyStatus == null) return null;
        return familyStatus.getTittle();
    }

    @Override
    public FamilyStatus convertToEntityAttribute(String s) {
        if(s == null) return null;
        switch (s) {
            case "Женат/Замужем" -> {
                return FamilyStatus.MARRIED;
            }
            case "Холост/Незамужем" -> {
                return FamilyStatus.UNMARRIED;
            }
            case "В разводе" -> {
                return FamilyStatus.DIVORCED;
            }
            case "Вдовец/Вдова" -> {
                return FamilyStatus.WIDOWER;
            }
            case "Сожительство/Несостоявшийся брак" -> {
                return FamilyStatus.FAILED_MARRIAGE;
            }
            case "Официальный брак с раздельным проживанием" -> {
                return FamilyStatus.MARRIAGE_WITH_SEPARATION;
            }
            case "Одиночество/Не в постоянных отношениях" -> {
                return FamilyStatus.LONELINESS;
            }
            case "Семьи с детьми" -> {
                return FamilyStatus.FAMILIES_WITH_CHILDREN;
            }
            default -> throw new IllegalArgumentException(s + " not supported.");
        }
    }
}
