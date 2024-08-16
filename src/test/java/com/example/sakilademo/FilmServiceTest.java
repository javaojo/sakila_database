package com.example.sakilademo;
import com.example.sakilademo.input.FilmInput;
import com.example.sakilademo.model.Film;
import com.example.sakilademo.model.Language;
import com.example.sakilademo.repository.FilmRepository;
import com.example.sakilademo.repository.LanguageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.example.sakilademo.service.FilmService;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private LanguageRepository languageRepository;


    @Mock
    private FilmInput filmInput;


    @InjectMocks
    private FilmService filmService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllFilmsShouldReturnAllFilms() {

        List<Film> films = List.of(new Film(), new Film());
        when(filmRepository.findAll()).thenReturn(films);

        List<Film> result = filmService.getAllFilms();

        assertEquals(films.size(), result.size());
    }

    @Test
    void getFilmByIdShouldReturnFilmWhenFilmExists() {

        short filmId = 1;
        Film film = new Film();
        when(filmRepository.findById(filmId)).thenReturn(Optional.of(film));

        Optional<Film> result = filmService.getFilmById(filmId);

        assertTrue(result.isPresent());
        assertEquals(film, result.get());
    }

    @Test
    void getFilmByIdShouldReturnEmptyWhenFilmDoesNotExist() {

        short filmId = 1;
        when(filmRepository.findById(filmId)).thenReturn(Optional.empty());

        Optional<Film> result = filmService.getFilmById(filmId);

        assertFalse(result.isPresent());
    }

    @Test
    void createFilmShouldSaveFilmWhenValidInput() {

        FilmInput filmInput = new FilmInput();
        filmInput.setTitle("Test Film");
        filmInput.setLanguageId((short) 1);

        Language language = new Language();
        when(languageRepository.findById(filmInput.getLanguageId())).thenReturn(Optional.of(language));

        Film film = new Film();
        when(filmRepository.save(any(Film.class))).thenReturn(film);

        Film result = filmService.createFilm(filmInput);

        assertNotNull(result);
    }

    @Test
    void createFilmShouldThrowExceptionWhenLanguageNotFound() {

        FilmInput filmInput = new FilmInput();
        filmInput.setTitle("Test Film");
        filmInput.setLanguageId((short) 10);

        when(languageRepository.findById(filmInput.getLanguageId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> filmService.createFilm(filmInput));

        verify(filmRepository, never()).save(any(Film.class));
    }

    @Test
    void updateFilmShouldUpdateFilmWhenFilmExists() {

        short filmId = 1;
        FilmInput filmInput = new FilmInput();
        filmInput.setTitle("Updated Title");
        filmInput.setLanguageId((short) 1);

        Film film = new Film();
        when(filmRepository.findById(filmId)).thenReturn(Optional.of(film));

        Language language = new Language();
        when(languageRepository.findById(filmInput.getLanguageId())).thenReturn(Optional.of(language));

        when(filmRepository.save(any(Film.class))).thenReturn(film);

        Film result = filmService.updateFilm(filmInput, filmId);

        assertNotNull(result);
        assertEquals(filmInput.getTitle(), film.getTitle());
    }

    @Test
    void deleteFilmShouldReturnTrueWhenFilmExists() {

        short filmId = 1;
        Film film = new Film();
        when(filmRepository.findById(filmId)).thenReturn(Optional.of(film));

        boolean result = filmService.deleteFilm(filmId);

        assertTrue(result);
    }



    @Test
    void updateFilmShouldThrowExceptionWhenFilmDoesNotExist() {

        short filmId = 1;
        FilmInput filmInput = new FilmInput();
        filmInput.setTitle("Updated Title");

        when(filmRepository.findById(filmId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> filmService.updateFilm(filmInput, filmId));

        verify(filmRepository, never()).save(any(Film.class));
    }

    @Test
    void updateFilmShouldThrowExceptionWhenLanguageNotFound() {

        short filmId = 1;
        FilmInput filmInput = new FilmInput();
        filmInput.setTitle("Updated Title");
        filmInput.setLanguageId((short) 10);

        Film film = new Film();
        when(filmRepository.findById(filmId)).thenReturn(Optional.of(film));
        when(languageRepository.findById(filmInput.getLanguageId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> filmService.updateFilm(filmInput, filmId));

        verify(filmRepository, never()).save(any(Film.class));
    }

    @Test
    void deleteFilmShouldReturnFalseWhenFilmDoesNotExist() {

        short filmId = 1;

        when(filmRepository.findById(filmId)).thenReturn(Optional.empty());

        boolean result = filmService.deleteFilm(filmId);

        assertFalse(result);
        verify(filmRepository, never()).delete(any(Film.class));
    }

    @Test
    void createFilmShouldThrowExceptionWhenLanguageIdIsNull() {

        FilmInput filmInput = new FilmInput();
        filmInput.setTitle("Test Film");

        assertThrows(IllegalArgumentException.class, () -> filmService.createFilm(filmInput));

        verify(filmRepository, never()).save(any(Film.class));
    }






}
