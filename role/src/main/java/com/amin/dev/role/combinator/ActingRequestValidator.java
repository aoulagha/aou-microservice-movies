package com.amin.dev.role.combinator;

import com.amin.dev.clients.role.ActorRequest;

import java.util.function.Function;

import static com.amin.dev.role.combinator.ValidationResult.*;

public interface ActingRequestValidator extends Function<ActorRequest, ValidationResult> {

    static ActingRequestValidator isRoleNameValid() {
        return actor -> actor.roleName().isEmpty() ?
                ROLE_INVALID : SUCCESS;
    }

    static ActingRequestValidator isActorEmpty() {
        return actor -> actor.idActor() == null ?
                ACTOR_INVALID : SUCCESS;
    }

    static ActingRequestValidator isMovieEmpty() {
        return actor -> actor.idMovie() == null ?
                MOVIE_INVALID : SUCCESS;
    }

    default ActingRequestValidator and (ActingRequestValidator other) {
        return actor -> {
            ValidationResult result = this.apply(actor);
            return result.equals(SUCCESS) ? other.apply(actor) : result;
        };
    }
}
