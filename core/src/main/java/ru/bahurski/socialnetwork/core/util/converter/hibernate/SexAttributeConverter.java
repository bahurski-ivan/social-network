package ru.bahurski.socialnetwork.core.util.converter.hibernate;

import ru.bahurski.socialnetwork.core.model.user.Sex;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * sex enum custom converter
 */
@Converter
public class SexAttributeConverter implements AttributeConverter<Sex, String> {
    @Override
    public String convertToDatabaseColumn(Sex attribute) {

        if (attribute == null)
            return null;

        switch (attribute) {
            case FEMALE:
                return "f";
            case MALE:
                return "m";
        }

        throw new IllegalStateException("added new value to Sex enum?");
    }

    @Override
    public Sex convertToEntityAttribute(String dbData) {
        if (dbData == null)
            return null;

        switch (dbData.toLowerCase()) {
            case "f":
                return Sex.FEMALE;
            case "m":
                return Sex.MALE;
        }

        return null;
    }
}
