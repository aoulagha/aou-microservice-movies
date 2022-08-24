package com.amin.dev.role;

import com.amin.dev.clients.role.ActorRequest;
import com.amin.dev.role.combinator.ValidationResult;
import com.amin.dev.role.error.ActingValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.amin.dev.role.combinator.ActingRequestValidator.*;

@Service
@AllArgsConstructor
@Slf4j
public class ActingService {

    private final ActingRepository repository;

    public Acting addActorMovie(final ActorRequest request) {

        // Validate request
        ValidationResult result =
                isActorEmpty()
                        .and(isMovieEmpty())
                        .and(isRoleNameValid())
                        .apply(request);

        if (!ValidationResult.SUCCESS.equals(result)) {
            throw new ActingValidationException(result.name());
        }

        // Verify not existing

        Optional<Acting> acting =
                repository.findByIdMovieAndIdActor(request.idMovie(), request.idActor());

        if (acting.isPresent()) {
            log.info("Acting is " +
                    "Already in " +
                    "database.");
            return acting.get();
        }

        // Add Acting
        Acting newActing =
                Acting.builder().idMovie(request.idMovie())
                        .idActor(request.idActor())
                        .roleName(request.roleName())
                        .build();

        return repository.saveAndFlush(newActing);

    }
}
