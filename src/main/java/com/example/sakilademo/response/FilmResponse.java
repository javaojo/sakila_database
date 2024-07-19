package com.example.sakilademo.response;

import com.example.sakilademo.model.Film;
import com.example.sakilademo.model.Language;
import com.example.sakilademo.model.Rating;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;

@Getter
public class FilmResponse {

    private final short id;
    private final String title;
    private final String description;
    private final Integer releaseYear;
    private final List<PartialActorResponse> actors;
    private final Language language;
    private final Language originalLanguage;
    private final Integer rentalDuration;
    private final Float rentalRate;
    private final Integer length;
    private final Float replacementCost;
    private final Rating rating;
    private final Timestamp lastUpdate;


    public FilmResponse(Film film) {
        this.id = film.getFilmId();
        this.title = film.getTitle();
        this.description = film.getDescription();
        this.releaseYear = film.getReleaseYear();
        this.actors = film.getCast().stream().map(PartialActorResponse::new).toList();
        this.language = film.getLanguage();
        this.originalLanguage = film.getOriginalLanguage();
        this.rentalDuration = film.getRentalDuration();
        this.rentalRate = film.getRentalRate();
        this.length = film.getLength();
        this.replacementCost = film.getReplacementCost();
        this.rating = film.getRating();
        this.lastUpdate = film.getLastUpdate();


    }


}
