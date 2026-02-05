package org.generation.italy.company.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BooleanToIntegerConverter implements AttributeConverter<Boolean, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Boolean aBoolean) {
        if (aBoolean == null) {
            return null;
        }
        return aBoolean ? 1 : 0;
    }

    @Override
    public Boolean convertToEntityAttribute(Integer integer) {
        if (integer == null) {
            return null;
        }
        return integer == 1;
    }
}
