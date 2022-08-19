package com.amin.dev.movies;

import com.amin.dev.amqp.RabbitMqMessageProducer;
import com.amin.dev.clients.artists.ArtistClient;
import com.amin.dev.clients.artists.ArtistRequest;
import com.amin.dev.clients.notification.NotificationClient;
import com.amin.dev.clients.notification.NotificationRequest;
import com.amin.dev.movies.combinator.ValidationResult;
import com.amin.dev.movies.error.MovieException;
import com.amin.dev.movies.error.MovieExistsException;
import com.amin.dev.movies.error.MovieValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.amin.dev.movies.combinator.MovieRequestValidator.*;

@Service
@Slf4j
@AllArgsConstructor
public class MovieService {

    private final MovieRepository repository;

    private final ArtistClient artistClient;
    private final RabbitMqMessageProducer messageProducer;

    /**
     *
     * @param movieRequest
     * @return
     * @throws MovieException
     */
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

        // Artist artist = artistClient.registerArtist(null);

        return repository.saveAndFlush(movie);
    }

    /**
     *
     * @param request
     * @throws MovieException
     */
    public void saveMovieWithArtists(MovieArtistsRequest request) throws MovieException {

        List<ArtistRequest> artists =  request.artists();
        MovieRequest movie =
                new MovieRequest(request.originalName(), request.frenchName(), request.yearRelease());
        log.info("Movie : " + movie);
        log.info("Artists : " + artists);

        // Movie
        Movie savedMovie =
                this.addNewMovie(movie);

        // get director list
        List<ArtistRequest> directorList = artists.stream()
                .filter(artistRequest -> "director".equals(artistRequest.role()))
                .toList();
        // get actor list
        List<ArtistRequest> actorList = artists.stream().filter(artistRequest -> "actor".equals(artistRequest.role()))
                .toList();

        // validate lists : at least one element on each list

        if (directorList.isEmpty() || actorList.isEmpty()) {
            throw new MovieValidationException("Actor or Director Empty");
        }

        Consumer<ArtistRequest> artistRequestConsumer = artistRequest -> {
            Integer integer = artistClient.registerArtist(artistRequest);
            System.out.println(artistRequest + "Id " + integer);
        };

        // Get distinct artist (case when director = actor)
        List<ArtistRequest> distinctArtists = artists.stream()
                .filter( distinctByKey(p -> p.firstName() + " " + p.lastName()) )
                .toList();

        // Save each artist
        distinctArtists.forEach(artistRequestConsumer);
        String message =
                String.format("Hi," +
                                " new" +
                                " " +
                                "movie was saved in the database management movie :  %s"
                        ,
                        savedMovie.getOriginalName());
        NotificationRequest notificationRequest = new NotificationRequest(savedMovie.getId(),
                savedMovie.getOriginalName(),
                "aoulagha",
                message);

        // Publish message
        // notification on the queue
        messageProducer.publish(notificationRequest,
                "internal.exchange",
                "movie.notif.routing-key");
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
