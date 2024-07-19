package com.example.sakilademo.utility;

import com.example.sakilademo.model.Rating;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RatingConverter implements AttributeConverter<Rating, String> {

    @Override
    public String convertToDatabaseColumn(Rating rating) {

        // converts the Rating enum values to its original string format
        // to be stored in the database
        if (rating == null) {
            return null;
        } else {
            return rating.getDisplayName();
        }
    }

    @Override
    public Rating convertToEntityAttribute(String dbData) {
        //  converts the string stored in the database to a Rating enum value
        //  that can be used in Java

        if (dbData == null) {
            return null;
        }
        return switch (dbData) {
            case "G" -> Rating.G;
            case "PG" -> Rating.PG;
            case "PG-13" -> Rating.PG_13;
            case "R" -> Rating.R;
            case "NC-17" -> Rating.NC_17;
            default -> throw new IllegalArgumentException("Unknown database value: " + dbData);
        };
    }
}
