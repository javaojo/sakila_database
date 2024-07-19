package com.example.sakilademo.response;

import com.example.sakilademo.model.Film;
import lombok.Getter;

@Getter
public class PartialFilmResponse {

    private final short id;
    private final String title;
    private final Integer releaseYear;



    public PartialFilmResponse(Film film) {
        this.title = film.getTitle();
        this.releaseYear = film.getReleaseYear();
        this.id = film.getFilmId();


    }


}
