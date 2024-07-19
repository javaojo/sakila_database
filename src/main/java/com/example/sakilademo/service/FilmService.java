package com.example.sakilademo.service;



import com.example.sakilademo.input.FilmInput;
import com.example.sakilademo.model.Film;
import com.example.sakilademo.model.Language;
import com.example.sakilademo.repository.FilmRepository;
import com.example.sakilademo.repository.LanguageRepository;
import com.example.sakilademo.response.FilmResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class  FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private LanguageRepository languageRepository;

    public List<FilmResponse> getAllFilms() {
        return filmRepository.findAll().stream().map(FilmResponse::new).toList();
    }

    public ResponseEntity<?> getFilmById(short id) {

        // find film by id
        Optional<Film> filmToFind = filmRepository.findById(id);

        // check if it exists
        if (filmToFind.isPresent()) {


            FilmResponse filmResponse = new FilmResponse(filmToFind.get());
            return ResponseEntity.ok(filmResponse);

            // return film
            //return ResponseEntity.ok(filmToFind.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Film not found with id: " + id);
        }
    }

    public ResponseEntity createFilm(FilmInput filmInput) {

        Film newFilm = new Film();
        newFilm.setTitle(filmInput.getTitle());
        newFilm.setDescription(filmInput.getDescription());
        newFilm.setReleaseYear(filmInput.getReleaseYear());
        newFilm.setRentalDuration(filmInput.getRentalDuration());
        newFilm.setRentalRate(filmInput.getRentalRate());
        newFilm.setLength(filmInput.getLength());
        newFilm.setReplacementCost(filmInput.getReplacementCost());
        newFilm.setRating(filmInput.getRating());
        newFilm.setLastUpdate(new Timestamp(System.currentTimeMillis()));


        // retrieve and set Language using languageId from filmInput
        if (filmInput.getLanguageId() != null) {
            Optional<Language> languageOptional = languageRepository.findById(filmInput.getLanguageId());
            if (languageOptional.isPresent()) {
                newFilm.setLanguage(languageOptional.get());
            } else {
                return ResponseEntity.badRequest().body("Language not found with ID: " + filmInput.getLanguageId());
            }
        } else {
            return ResponseEntity.badRequest().body("Language ID must not be null.");
        }

        if (filmInput.getOriginalLanguageId() != null) {
            Optional<Language> originaLanguageOptional = languageRepository.findById(filmInput.getOriginalLanguageId());
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            if (originaLanguageOptional.isPresent()) {
                newFilm.setOriginalLanguage(originaLanguageOptional.get());
                System.out.println(originaLanguageOptional.get());
                System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
            } else {
                return ResponseEntity.badRequest().body("Language not found with ID: " + filmInput.getOriginalLanguageId());
            }
        } else {
            return ResponseEntity.badRequest().body("Language ID must not be null.");
        }


        // save to db
        Film savedFilm = filmRepository.save(newFilm);

        // fetch the film from the database using its id
        Optional<Film> optionalFilm = filmRepository.findById(savedFilm.getFilmId());

        // check if the film was successfully saved and retrieved
        if (optionalFilm.isPresent()) {
            // return in the response
            return ResponseEntity.ok(optionalFilm.get());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve saved film.");
        }
    }

    public ResponseEntity<?> updateFilm(FilmInput newFilm, short id) {

        // find the film to update by its ID
        Optional<Film> filmToUpdate = filmRepository.findById(id);

        // check if the film with the given ID exists
        if (filmToUpdate.isPresent()) {
            // retrieve the existing film from Optional
            Film existingFilm = filmToUpdate.get();

            // update film details from the incoming newFilm object
            existingFilm.setTitle(newFilm.getTitle());
            existingFilm.setDescription(newFilm.getDescription());
            existingFilm.setReleaseYear(newFilm.getReleaseYear());
            existingFilm.setRentalDuration(newFilm.getRentalDuration());
            existingFilm.setRentalRate(newFilm.getRentalRate());
            existingFilm.setLength(newFilm.getLength());
            existingFilm.setReplacementCost(newFilm.getReplacementCost());
            existingFilm.setRating(newFilm.getRating());
            existingFilm.setLastUpdate(new Timestamp(System.currentTimeMillis()));

            // update language using languageId from newFilm
            Short languageId = newFilm.getLanguageId();
            if (languageId != null) {
                Optional<Language> languageOptional = languageRepository.findById(languageId);
                if (languageOptional.isPresent()) {
                    existingFilm.setLanguage(languageOptional.get());
                } else {
                    return ResponseEntity.badRequest().body("Language not found with ID: " + languageId);
                }
            } else {
                return ResponseEntity.badRequest().body("Language ID must not be null.");
            }

            // update original language using originalLanguageId from newFilm
            Short originalLanguageId = newFilm.getOriginalLanguageId();
            if (originalLanguageId != null) {
                Optional<Language> originalLanguageOptional = languageRepository.findById(originalLanguageId);
                if (originalLanguageOptional.isPresent()) {
                    existingFilm.setOriginalLanguage(originalLanguageOptional.get());
                } else {
                    return ResponseEntity.badRequest().body("Original language not found with ID: " + originalLanguageId);
                }
            } else {
                existingFilm.setOriginalLanguage(null);
            }

            // save the updated film in the database
            filmRepository.save(existingFilm);

            // return HTTP response with updated film wrapped in a FilmResponse object
            return ResponseEntity.ok(new FilmResponse(existingFilm));
        } else {
            // return 404 Not Found response if no film exists with the given ID
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No matching film to update exists.");
        }
    }

    public ResponseEntity<?> deleteFilm(short id) {
        // find film by id
        Optional<Film> filmToDelete = filmRepository.findById(id);

        if (filmToDelete.isPresent()) {

            // delete if found
            filmRepository.delete(filmToDelete.get());
            return ResponseEntity.ok("Film deleted");
        }else {
            //return no matching film by id
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No matching film to delete exists.");
        }
    }
}