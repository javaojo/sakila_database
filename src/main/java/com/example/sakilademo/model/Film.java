package com.example.sakilademo.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


import com.example.sakilademo.utility.RatingConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "film")
public class Film {

    @Id
    @Column(name = "film_id")
    @Getter
    private short filmId;

    @Setter
    @Getter
    @Column(name = "title")
    private String title;

    @Setter
    @Getter
    @Column(name = "description")
    private String description;

    @Setter
    @Getter
    @Column(name = "release_year")
    private Integer releaseYear;


    @Setter
    @Getter
    @ManyToMany(mappedBy = "films")
    private List<Actor> cast = new ArrayList<>();

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "original_language_id")
    @JsonProperty("originalLanguage")
    private Language originalLanguage;

    @Setter
    @Getter
    @Column(name = "rental_duration")
    private Integer rentalDuration;

    @Setter
    @Getter
    @Column(name = "rental_rate")
    private Float rentalRate;

    @Setter
    @Getter
    @Column(name = "length")
    private Integer length;

    @Setter
    @Getter
    @Column(name = "replacement_cost")
    private Float replacementCost;

    @Setter
    @Getter
    @Column(name = "rating")
    @Convert(converter = RatingConverter.class)
    private Rating rating;

    @Setter
    @Getter
    @Column(name = "last_update")
    private Timestamp lastUpdate;


//    @Setter
//    @Getter
//    @Column(name = "special_features")
//    @Enumerated(EnumType.STRING)
//    private Collection<SpecialFeature> specialFeatures;

}

