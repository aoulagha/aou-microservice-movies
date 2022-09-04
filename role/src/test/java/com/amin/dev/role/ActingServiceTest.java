package com.amin.dev.role;

import com.amin.dev.clients.role.ActorRequest;
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
class ActingServiceTest {

    @Mock
    ActingRepository repository;

    @Autowired
    private ActingService service;

    @BeforeEach
    void setUp() {
        service = new ActingService(repository);
    }

    @Test
    public void canAddNonExistingActing() {
        // given
        String roleName = "Mean " +
                "Streets";
        Integer idActor = 1;
        Integer idMovie = 1;
        ActorRequest request =
                new ActorRequest(idMovie, idActor, roleName);

        given(repository.findByIdMovieAndIdActor(idMovie, idActor))
                .willReturn(Optional.empty());

        // when
        service.addActorMovie(request);

        // then
        ArgumentCaptor<Acting> sACaptor =
                ArgumentCaptor.forClass(Acting.class);
        BDDMockito.verify(repository).saveAndFlush(sACaptor.capture());

        Acting getActing =
                sACaptor.getValue();
        assertThat(getActing).isNotNull();
        assertThat(getActing.getRoleName()).isEqualTo(roleName);
    }

    @Test
    public void addActingGettingWhenExisting() {
        // given
        String roleName = "Mean " +
                "Streets";
        Integer idActor = 1;
        Integer idMovie = 1;
        Integer idActing = 1;
        ActorRequest request =
                new ActorRequest(idMovie, idActor, roleName);
        Acting role = Acting.builder()
                .id(idActing)
                .idMovie(idMovie)
                .idActor(idActor)
                .roleName(roleName).build();

        given(repository.findByIdMovieAndIdActor(idMovie, idActor))
                .willReturn(Optional.of(role));

        // when
        Acting getArtist =
                service.addActorMovie(request);

        assertThat(getArtist).isEqualTo(role);

        // then
        verify(repository, never()).saveAndFlush(any());
    }

    @Test
    public void fireExceptionWhenRoleNonValid() {
        // given
        String roleName = "";
        Integer idActor = 1;
        Integer idMovie = 1;

        ActorRequest request =
                new ActorRequest(idMovie, idActor, roleName);

        // when

        // then
        assertThatThrownBy(() -> service.addActorMovie(request)).isInstanceOf(ActingValidationException.class)
                .hasMessage(ValidationResult.ROLE_INVALID.name());

        // then
        verify(repository, never()).saveAndFlush(any());
    }
}