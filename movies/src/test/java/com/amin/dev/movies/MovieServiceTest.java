package com.amin.dev.movies;

import com.amin.dev.movies.combinator.ValidationResult;
import com.amin.dev.movies.error.MovieExistsException;
import com.amin.dev.movies.error.MovieValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    MovieRepository repository;

    @Autowired
    private MovieService service;

    @BeforeEach
    void setUp() {
        service =
                new MovieService(repository, null, null, null);
    }

    @Test
    public void canAddNonExistingMovie() throws MovieExistsException {
        // given
        String movieName = "Pasqualino";
        MovieRequest movie = new MovieRequest(movieName, null, 1973);
        given(repository.findByOriginalNameContainingIgnoreCase(movie.originalName().toLowerCase()))
                .willReturn(Optional.empty());

        // when
        service.addNewMovie(movie);

        // then
        ArgumentCaptor<Movie> sACaptor = ArgumentCaptor.forClass(Movie.class);
        BDDMockito.verify(repository).saveAndFlush(sACaptor.capture());

        Movie getMovie = sACaptor.getValue();
        assertThat(getMovie).isNotNull();
        assertThat(getMovie.getOriginalName()).isEqualTo(movieName);
    }

    @Test
    public void cannotAddAnExistingMovieAndThrowException() {
        // given
        String movieName = "PASQUALINO";
        MovieRequest movie = new MovieRequest(movieName, null, 1973);

        Movie dbMovie = Movie.builder().originalName("Pasqualino")
                .frenchName(null).releaseDate(1973).build();

        given(repository.findByOriginalNameContainingIgnoreCase(movie.originalName().toLowerCase()))
                .willReturn(Optional.of(dbMovie));

        // then
        assertThatThrownBy(() -> service.addNewMovie(movie)).isInstanceOf(MovieExistsException.class)
                .hasMessage(movieName.toLowerCase() + " is Present in Database");
        verify(repository, never()).save(any());
    }

    @Test
    public void fireExceptionWhenYearIsNotValid() {
        // given
        String movieName = "Pasqualino";
        MovieRequest movie = new MovieRequest(movieName, null, 1800);

        // when

        // then
        assertThatThrownBy(() -> service.addNewMovie(movie)).isInstanceOf(MovieValidationException.class)
                .hasMessage("Validation Issue : " + ValidationResult.YEAR_NOT_VALID.name());
        verify(repository, never()).save(any());
    }

    @Test
    public void fireExceptionWhenTitleIsNotValid() {
        // given
        String movieName = "";
        MovieRequest movie = new MovieRequest(movieName, null, 1800);

        // when

        // then
        assertThatThrownBy(() -> service.addNewMovie(movie)).isInstanceOf(MovieValidationException.class)
                .hasMessage("Validation Issue : " + ValidationResult.ORIGINAL_NAME_NOT_VALID.name());
        verify(repository, never()).save(any());
    }


}