package com.example.sakilademo.input;

import com.example.sakilademo.model.Rating;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class FilmInput {

    private short id;

    @NotNull
    @Size(min = 1, max = 255)
    private String title;

    @NotNull
    @Size(min = 1, max = 5000)
    private String description;

    @Min(1888)
    private Integer releaseYear;


    private Short actorIds;


    @Min(0)
    @Max(255)
    @JsonProperty("language")
    private Short languageId;

    @Min(0)
    @Max(255)
    @JsonProperty("originalLanguage")
    private Short originalLanguageId;

    @NotNull
    @Min(0)
    @Max(365)
    private Integer rentalDuration;

    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "9999.99")
    private Float rentalRate;

    @NotNull
    @Min(1)
    @Max(1000)
    private Integer length;

    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "999.99")
    private Float replacementCost;

    @NotNull
    private Rating rating;

}