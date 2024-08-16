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
public class FilmController {


    @Autowired
    private FilmService filmService;


    @GetMapping("")
    @CrossOrigin(origins = "*")
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
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> getFilmById(@PathVariable Short id) {
        Optional<Film> film = filmService.getFilmById(id);

        // Check if the film is present
        if (film.isPresent()) {
            Film f = film.get();
            FilmResponse filmResponse = new FilmResponse(f);
            return ResponseEntity.ok(filmResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Film not found with id: " + id);
        }
    }

    @PostMapping("")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> createFilm(@RequestBody FilmInput filmInput) {
        try {
            Film createdFilm = filmService.createFilm(filmInput);
            return ResponseEntity.ok(new FilmResponse(createdFilm));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> deleteFilm(@PathVariable short id) {
        boolean deleted = filmService.deleteFilm(id);
        if (deleted) {
            return ResponseEntity.ok("Film deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No matching film to delete exists.");
        }
    }

    @PutMapping("/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> updateFilm(@RequestBody FilmInput newFilm, @PathVariable short id) {
        try {
            Film updatedFilm = filmService.updateFilm(newFilm, id);
            return ResponseEntity.ok(new FilmResponse(updatedFilm));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
