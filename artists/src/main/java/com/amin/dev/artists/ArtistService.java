package com.amin.dev.artists;

import com.amin.dev.artists.combinator.ArtistRequestValidator;
import com.amin.dev.artists.combinator.ValidationResult;
import com.amin.dev.artists.error.ArtistValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.amin.dev.artists.combinator.ArtistRequestValidator.*;

@Service
@Slf4j
@AllArgsConstructor
public class ArtistService {

    private final ArtistRepository repository;

    /**
     * @param artistRequest Param Artist
     * @return the Artist created or if exists
     */
    public Artist addArtist(ArtistRequest artistRequest) {
        // Validate Artist
        ValidationResult result = isNameValid()
                .and(isYearValid())
                .apply(artistRequest);

        if (!ValidationResult.SUCCESS.equals(result)) {
            throw new ArtistValidationException(result.name());
        }

        // Verify existence
        Optional<Artist> artist = repository.searchByFirstNameIgnoreCaseAndLastNameIgnoreCase(
                artistRequest.firstName(), artistRequest.lastName()
        );
        if (artist.isPresent()) {
            log.info("Artist Already in database.");
            return artist.get();
        }

        // Add new Artist
        Artist newArtist = Artist.builder().firstName(artistRequest.firstName())
                .lastName(artistRequest.lastName())
                .birthDate(artistRequest.birthDate()).build();

        return repository.saveAndFlush(newArtist);
    }
}
