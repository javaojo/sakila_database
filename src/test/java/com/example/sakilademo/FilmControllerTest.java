package com.example.sakilademo;
import com.example.sakilademo.controller.FilmController;
import com.example.sakilademo.model.Film;
import com.example.sakilademo.response.FilmResponse;
import com.example.sakilademo.service.FilmService;
import com.example.sakilademo.input.FilmInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilmControllerTest {

    @Mock
    private FilmService filmService;

    @InjectMocks
    private FilmController filmController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllFilmsShouldReturnListOfFilms() {

        List<Film> films = List.of(new Film(), new Film(), new Film());

        when(filmService.getAllFilms()).thenReturn(films);

        ResponseEntity<List<FilmResponse>> response = filmController.getAllFilms();

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertInstanceOf(List.class, response.getBody());

        for (FilmResponse filmResponse : response.getBody()) {
            assertInstanceOf(FilmResponse.class, filmResponse);
        }

    }

    @Test
    void getFilmByIdShouldReturnFilmWhenFilmExists() {

        short filmId = 1;
        Film film = new Film();
        when(filmService.getFilmById(filmId)).thenReturn(Optional.of(film));

        ResponseEntity<?> response = filmController.getFilmById(filmId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(FilmResponse.class, response.getBody());


    }

    @Test
    void getFilmByIdShouldReturnNotFoundWhenFilmDoesNotExist() {

        short filmId = 1;
        when(filmService.getFilmById(filmId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = filmController.getFilmById(filmId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Film not found with id: " + filmId, response.getBody());


    }

    @Test
    void createFilmShouldReturnCreatedFilmWhenInputIsValid() {

        FilmInput filmInput = new FilmInput();
        Film film = new Film();

        when(filmService.createFilm(filmInput)).thenReturn(film);

        ResponseEntity<?> response = filmController.createFilm(filmInput);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(FilmResponse.class, response.getBody());

    }

    @Test
    void createFilmShouldReturnBadRequestWhenInputIsInvalid() {

        FilmInput filmInput = new FilmInput();
        String errorMessage = "Language not found with ID: 1";

        when(filmService.createFilm(filmInput)).thenThrow(new IllegalArgumentException(errorMessage));

        ResponseEntity<?> response = filmController.createFilm(filmInput);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());

    }

    @Test
    void deleteFilmShouldReturnOkWhenFilmIsDeleted() {

        short filmId = 1;
        when(filmService.deleteFilm(filmId)).thenReturn(true);

        ResponseEntity<?> response = filmController.deleteFilm(filmId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Film deleted", response.getBody());

    }

    @Test
    void deleteFilmShouldReturnNotFoundWhenFilmDoesNotExist() {

        short filmId = 1;
        when(filmService.deleteFilm(filmId)).thenReturn(false);

        ResponseEntity<?> response = filmController.deleteFilm(filmId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No matching film to delete exists.", response.getBody());

    }

    @Test
    void updateFilmShouldReturnUpdatedFilmWhenInputIsValid() {

        short filmId = 1;
        FilmInput filmInput = new FilmInput();
        Film film = new Film();
        when(filmService.updateFilm(filmInput, filmId)).thenReturn(film);

        ResponseEntity<?> response = filmController.updateFilm(filmInput, filmId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(FilmResponse.class, response.getBody());

    }

    @Test
    void updateFilmShouldReturnBadRequestWhenInputIsInvalid() {

        short filmId = 99;
        FilmInput filmInput = new FilmInput();
        String errorMessage = "No matching film to update exists.";
        when(filmService.updateFilm(filmInput, filmId)).thenThrow(new IllegalArgumentException(errorMessage));

        ResponseEntity<?> response = filmController.updateFilm(filmInput, filmId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());

    }
}