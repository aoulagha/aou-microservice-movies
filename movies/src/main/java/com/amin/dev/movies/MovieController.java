package com.amin.dev.movies;

import com.amin.dev.movies.error.MovieExistsException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/manager")
@AllArgsConstructor
public class MovieController {

    private final MovieService service;

    @PostMapping("/movies")
    public Movie registerMovie(@RequestBody MovieRequest movieRequest) throws MovieExistsException {
        log.info("Register New Movie : " + movieRequest.originalName());
        return service.addNewMovie(movieRequest);
    }

    @PostMapping("/artmovies")
    public void registerMovieArtists(@RequestBody MovieArtistsRequest movieRequest) {
        log.info("Register New Movie with artists : " + movieRequest.originalName());
        service.saveMovieWithArtists(movieRequest);
    }


}
