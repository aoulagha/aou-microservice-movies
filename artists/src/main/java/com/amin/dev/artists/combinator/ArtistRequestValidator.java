package com.amin.dev.artists.combinator;

import com.amin.dev.artists.ArtistRequest;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.amin.dev.artists.combinator.ValidationResult.*;

public interface ArtistRequestValidator extends Function<ArtistRequest, ValidationResult> {

    static ArtistRequestValidator isNameValid() {
        return artistRequest -> artistRequest.lastName().isEmpty() ||
                artistRequest.firstName().isEmpty() ? NAME_INVALID : SUCCESS;
    }

    static ArtistRequestValidator isYearValid() {
        return artistRequest -> {
            LocalDate bDate = artistRequest.birthDate();
            Function<LocalDate, ValidationResult> localDateConsumer = date
                    -> {
                if (Optional.ofNullable(date).isEmpty())
                    return YEAR_NOT_VALID;
                Integer year = date.getYear();
                ValidationResult result = YEAR_NOT_VALID;
                if (year > 1850 && year <= LocalDate.now().getYear()) {
                    result = SUCCESS;
                }
                return result;
            };
            return localDateConsumer.apply(artistRequest.birthDate());
        };
    }

    default ArtistRequestValidator and (ArtistRequestValidator other) {
        return customer -> {
            ValidationResult result = this.apply(customer);
            return result.equals(SUCCESS) ? other.apply(customer) : result;
        };
    }

}
