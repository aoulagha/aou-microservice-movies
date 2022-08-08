package com.amin.dev.movies.combinator;

import com.amin.dev.movies.MovieRequest;

import java.time.LocalDate;
import java.util.function.Function;

import static com.amin.dev.movies.combinator.ValidationResult.*;

public interface MovieRequestValidator extends Function<MovieRequest, ValidationResult> {

    static MovieRequestValidator isTitleValid() {
        return movie -> movie.originalName().isEmpty() ? ORIGINAL_NAME_NOT_VALID : SUCCESS;
    }

    static MovieRequestValidator isYearValid() {
        return movie -> {
            Integer year = movie.yearRelease();
            if (year > 1900 && year <= LocalDate.now().getYear()) {
                return SUCCESS;
            } else {
                return YEAR_NOT_VALID;
            }
        };
    }

    // Combinator Pattern
    default MovieRequestValidator and (MovieRequestValidator other) {
        return customer -> {
            ValidationResult result = this.apply(customer);
            return result.equals(SUCCESS) ? other.apply(customer) : result;
        };
    }
}
