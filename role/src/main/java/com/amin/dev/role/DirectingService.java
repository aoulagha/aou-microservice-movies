package com.amin.dev.role;

import com.amin.dev.clients.role.DirectorRequest;
import com.amin.dev.role.combinator.ValidationResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.amin.dev.role.combinator.DirectingRequestValidator.isDirectorEmpty;
import static com.amin.dev.role.combinator.DirectingRequestValidator.isMovieEmpty;

@Service
@Slf4j
@AllArgsConstructor
public class DirectingService {

    private final DirectingRepository repository;

    /**
     *
     * @param request
     * @return
     */
    public Directing addDirectorMovie(final DirectorRequest request) {

        // Validate request
        ValidationResult result =
                isMovieEmpty()
                        .and(isDirectorEmpty())
                        .apply(request);

        if (!ValidationResult.SUCCESS.equals(result)) {
            throw new DirectingValidationException(result.name());
        }

        // Verify not existing
        Optional<Directing> directing = repository
                .findByIdMovieAndIdDirector(request.idMovie(),
                        request.idDirector());

        if (directing.isPresent()) {
            log.info("Directing is " +
                    "Already in " +
                    "database.");
            return directing.get();
        }

        // Add Directing
        Directing director = Directing.builder()
                .idMovie(request.idMovie())
                .idDirector(request.idDirector())
                .build();

        return repository.saveAndFlush(director);
    }


}
