package com.example.sakilademo.controller;

import com.example.sakilademo.model.Film;
import com.example.sakilademo.response.FilmResponse;
import com.example.sakilademo.service.FilmService;
import com.example.sakilademo.input.FilmInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/films")
@CrossOrigin(origins = "*")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @GetMapping("")
    public ResponseEntity<List<FilmResponse>> getAllFilms() {
        List<Film> films = filmService.getAllFilms();
        List<FilmResponse> filmResponses = new ArrayList<>();

        for (Film film : films) {
            FilmResponse filmResponse = new FilmResponse(film);
            filmResponses.add(filmResponse);
        }

        return ResponseEntity.ok(filmResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmResponse> getFilmById(@PathVariable Short id) {
        Optional<Film> film = filmService.getFilmById(id);

        if (film.isPresent()) {
            FilmResponse filmResponse = new FilmResponse(film.get());
            return ResponseEntity.ok(filmResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @PostMapping("")
    public ResponseEntity<FilmResponse> createFilm(@RequestBody FilmInput filmInput) {
        try {
            Film createdFilm = filmService.createFilm(filmInput);
            FilmResponse filmResponse = new FilmResponse(createdFilm);
            return ResponseEntity.ok(filmResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFilm(@PathVariable short id) {
        boolean deleted = filmService.deleteFilm(id);
        if (deleted) {
            return ResponseEntity.ok("Film deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No matching film to delete exists.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilmResponse> updateFilm(@RequestBody FilmInput newFilm, @PathVariable short id) {
        try {
            Film updatedFilm = filmService.updateFilm(newFilm, id);
            FilmResponse filmResponse = new FilmResponse(updatedFilm);
            return ResponseEntity.ok(filmResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
