package com.example.sakilademo.controller;


import com.example.sakilademo.response.FilmResponse;
import com.example.sakilademo.service.FilmService;
import com.example.sakilademo.utility.ValidationGroup;
import com.example.sakilademo.input.FilmInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {


    @Autowired
    private FilmService filmService;


    @GetMapping("")
    public List<FilmResponse> getAllFilms() {
        return filmService.getAllFilms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFilmById(@Validated(ValidationGroup.Create.class)@PathVariable Short id) {
        return filmService.getFilmById(id);
    }

    @PostMapping("")
    public ResponseEntity createFilm(@RequestBody FilmInput film) {
        return filmService.createFilm(film);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFilm (@Validated(ValidationGroup.Delete.class)@PathVariable short id) {
        return filmService.deleteFilm(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFilm(@Validated(ValidationGroup.Create.class) @RequestBody FilmInput newFilm, @PathVariable short id) {
        return filmService.updateFilm(newFilm, id);
    }


}
