package com.amin.dev.artists;

import com.amin.dev.artists.combinator.ValidationResult;
import com.amin.dev.artists.error.ArtistValidationException;
import com.amin.dev.clients.artists.ArtistRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ArtistServiceTest {

    @Mock
    ArtistRepository repository;

    @Autowired
    private ArtistService service;

    @BeforeEach
    void setUp() {
        service = new ArtistService(repository);
    }

    @Test
    public void canAddNonExistingArtist() {
        // given
        String firstName = "John";
        String lastName = "Pesci";
        LocalDate bDate = LocalDate.of(1946, 2, 9);
        ArtistRequest request = new ArtistRequest(firstName, lastName, bDate, "actor");
        given(repository.searchByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName))
                .willReturn(Optional.empty());

        // when
        service.addArtist(request);

        // then
        ArgumentCaptor<Artist> sACaptor = ArgumentCaptor.forClass(Artist.class);
        BDDMockito.verify(repository).saveAndFlush(sACaptor.capture());

        Artist getArtist = sACaptor.getValue();
        assertThat(getArtist).isNotNull();
        assertThat(getArtist.getFirstName()).isEqualTo(firstName);
    }

    @Test
    public void addArtistGetArtistWhenExisting() {
        // given
        String firstName = "John";
        String lastName = "Pesci";
        LocalDate bDate = LocalDate.of(1946, 2, 9);
        Integer idArtist = 1;
        ArtistRequest request = new ArtistRequest(firstName, lastName, bDate, "actor");

        Artist artist = Artist.builder().firstName(firstName)
                .lastName(lastName)
                .birthDate(bDate)
                .id(idArtist).build();

        given(repository.searchByFirstNameIgnoreCaseAndLastNameIgnoreCase(firstName, lastName))
                .willReturn(Optional.of(artist));

        // when
        Artist getArtist = service.addArtist(request);

        assertThat(getArtist).isEqualTo(artist);

        // then
        verify(repository, never()).saveAndFlush(any());
    }

    @Test
    public void fireExceptionWhenNameNonValid() {
        // given
        String firstName = "";
        String lastName = "Pesci";
        LocalDate bDate = LocalDate.of(1946, 2, 9);
        ArtistRequest request = new ArtistRequest(firstName, lastName, bDate, "actor");

        // when

        // then
        assertThatThrownBy(() -> service.addArtist(request)).isInstanceOf(ArtistValidationException.class)
                .hasMessage(ValidationResult.NAME_INVALID.name());
        verify(repository, never()).save(any());
    }

    @Test
    public void fireExceptionWhenDateBNonValid() {
        // given
        String firstName = "John";
        String lastName = "Pesci";
        LocalDate bDate = LocalDate.of(2023, 2, 9);
        ArtistRequest request = new ArtistRequest(firstName, lastName, bDate, "actor");

        // when

        // then
        assertThatThrownBy(() -> service.addArtist(request)).isInstanceOf(ArtistValidationException.class)
                .hasMessage(ValidationResult.YEAR_NOT_VALID.name());
        verify(repository, never()).save(any());
    }

}