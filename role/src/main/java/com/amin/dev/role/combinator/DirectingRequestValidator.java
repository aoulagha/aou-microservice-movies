package com.amin.dev.role.combinator;

import com.amin.dev.clients.role.DirectorRequest;

import java.util.function.Function;

import static com.amin.dev.role.combinator.ValidationResult.*;

public interface DirectingRequestValidator extends Function<DirectorRequest, ValidationResult> {

    static DirectingRequestValidator isDirectorEmpty() {
        return director -> director.idDirector() == null
                ? DIRECTOR_INVALID : SUCCESS;
    }

    static DirectingRequestValidator isMovieEmpty() {
        return director -> director.idMovie() == null
                ? MOVIE_INVALID : SUCCESS;
    }

    default DirectingRequestValidator and (DirectingRequestValidator other) {
        return director -> {
            ValidationResult result = this.apply(director);
            return result.equals(SUCCESS) ? other.apply(director) : result;
        };
    }
}
