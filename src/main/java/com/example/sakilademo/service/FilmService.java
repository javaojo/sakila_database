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

    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    public Optional<Film> getFilmById(short id) {
        return filmRepository.findById(id);
    }

    public Film createFilm(FilmInput filmInput) {
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

        if (filmInput.getLanguageId() != null) {
            Optional<Language> languageOptional = languageRepository.findById(filmInput.getLanguageId());
            if (languageOptional.isPresent()) {
                newFilm.setLanguage(languageOptional.get());
            } else {
                throw new IllegalArgumentException("Language not found with ID: " + filmInput.getLanguageId());
            }
        } else {
            throw new IllegalArgumentException("Language ID must not be null.");
        }

        if (filmInput.getOriginalLanguageId() != null) {
            Optional<Language> originalLanguageOptional = languageRepository.findById(filmInput.getOriginalLanguageId());
            if (originalLanguageOptional.isPresent()) {
                newFilm.setOriginalLanguage(originalLanguageOptional.get());
            } else {
                throw new IllegalArgumentException("Original language not found with ID: " + filmInput.getOriginalLanguageId());
            }
        } else {
            newFilm.setOriginalLanguage(null);
        }

        return filmRepository.save(newFilm);
    }


    public Film updateFilm(FilmInput newFilm, short id) {
        Optional<Film> filmToUpdate = filmRepository.findById(id);

        if (filmToUpdate.isPresent()) {
            Film existingFilm = filmToUpdate.get();
            existingFilm.setTitle(newFilm.getTitle());
            existingFilm.setDescription(newFilm.getDescription());
            existingFilm.setReleaseYear(newFilm.getReleaseYear());
            existingFilm.setRentalDuration(newFilm.getRentalDuration());
            existingFilm.setRentalRate(newFilm.getRentalRate());
            existingFilm.setLength(newFilm.getLength());
            existingFilm.setReplacementCost(newFilm.getReplacementCost());
            existingFilm.setRating(newFilm.getRating());
            existingFilm.setLastUpdate(new Timestamp(System.currentTimeMillis()));

            Short languageId = newFilm.getLanguageId();
            if (languageId != null) {
                Optional<Language> languageOptional = languageRepository.findById(languageId);
                if (languageOptional.isPresent()) {
                    existingFilm.setLanguage(languageOptional.get());
                } else {
                    throw new IllegalArgumentException("Language not found with ID: " + languageId);
                }
            } else {
                throw new IllegalArgumentException("Language ID must not be null.");
            }

            Short originalLanguageId = newFilm.getOriginalLanguageId();
            if (originalLanguageId != null) {
                Optional<Language> originalLanguageOptional = languageRepository.findById(originalLanguageId);
                if (originalLanguageOptional.isPresent()) {
                    existingFilm.setOriginalLanguage(originalLanguageOptional.get());
                } else {
                    throw new IllegalArgumentException("Original language not found with ID: " + originalLanguageId);
                }
            } else {
                existingFilm.setOriginalLanguage(null);
            }

            return filmRepository.save(existingFilm);
        } else {
            throw new IllegalArgumentException("No matching film to update exists.");
        }
    }


    public boolean deleteFilm(short id) {
        Optional<Film> filmToDelete = filmRepository.findById(id);

        if (filmToDelete.isPresent()) {
            filmRepository.delete(filmToDelete.get());
            return true;
        } else {
            return false;
        }
    }
}