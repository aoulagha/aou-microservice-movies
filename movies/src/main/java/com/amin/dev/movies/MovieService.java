package com.amin.dev.movies;

import com.amin.dev.movies.combinator.ValidationResult;
import com.amin.dev.movies.error.MovieException;
import com.amin.dev.movies.error.MovieExistsException;
import com.amin.dev.movies.error.MovieValidationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.amin.dev.movies.combinator.MovieRequestValidator.*;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository repository;

    public Movie addNewMovie(MovieRequest movieRequest) throws MovieException {
        // Validate the Movie
        ValidationResult result = isTitleValid()
                .and(isYearValid())
                .apply(movieRequest);

        if (!ValidationResult.SUCCESS.equals(result)) {
            throw new MovieValidationException(result.name());
        }

        // Verify the existence of the movie
        String lowerName = movieRequest.originalName().toLowerCase();
        Optional<Movie> byOriginalName = repository.findByOriginalNameContainingIgnoreCase(lowerName);

        if (byOriginalName.isPresent()) {
            throw new MovieExistsException(lowerName + " is Present in Database");
        }

        // Adding the movie
        Movie movie = Movie.builder().originalName(movieRequest.originalName())
                        .frenchName(movieRequest.frenchName())
                                .releaseDate(movieRequest.yearRelease()).build();

        return repository.saveAndFlush(movie);
    }
}
