package com.amin.dev.role;

import com.amin.dev.clients.role.DirectorRequest;
import com.amin.dev.role.combinator.ValidationResult;
import com.amin.dev.role.error.ActingValidationException;
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
class DirectingServiceTest {

    @Mock
    DirectingRepository repository;

    @Autowired
    private DirectingService service;

    @BeforeEach
    void setUp() {
        service = new DirectingService(repository);
    }

    @Test
    public void canAddNonExistingDirecting() {
        // given
        Integer idDirector = 1;
        Integer idMovie = 1;
        DirectorRequest request =
                new DirectorRequest(idMovie, idDirector);

        given(repository.findByIdMovieAndIdDirector(idMovie, idDirector))
                .willReturn(Optional.empty());

        // when
        service.addDirectorMovie(request);

        // then
        ArgumentCaptor<Directing> sACaptor =
                ArgumentCaptor.forClass(Directing.class);
        BDDMockito.verify(repository).saveAndFlush(sACaptor.capture());

        Directing getDirector =
                sACaptor.getValue();
        assertThat(getDirector).isNotNull();
        assertThat(getDirector.getIdDirector()).isEqualTo(idDirector);
    }

    @Test
    public void addDirectingGettingWhenExisting() {
        // given
        Integer idDirector = 1;
        Integer idMovie = 2;
        Integer idDirecting = 1;
        DirectorRequest request =
                new DirectorRequest(idMovie, idDirector);
        Directing directing =
                Directing.builder()
                        .id(idDirecting)
                        .idMovie(idMovie)
                        .idDirector(idDirector)
                        .build();

        given(repository.findByIdMovieAndIdDirector(idMovie,
                idDirector))
                .willReturn(Optional.of(directing));

        // when
        Directing getArtist =
                service.addDirectorMovie(request);

        assertThat(getArtist).isEqualTo(directing);

        // then
        verify(repository, never()).saveAndFlush(any());
    }

    @Test
    public void fireExceptionWhenDirectorNonValid() {
        // given
        Integer idDirector = 1;
        Integer idMovie = null;

        DirectorRequest request =
                new DirectorRequest(idMovie, idDirector);

        // when

        // then
        assertThatThrownBy(() -> service.addDirectorMovie(request))
                .isInstanceOf(DirectingValidationException.class)
                .hasMessage(ValidationResult.MOVIE_INVALID.name());

        // then
        verify(repository, never()).saveAndFlush(any());
    }

}