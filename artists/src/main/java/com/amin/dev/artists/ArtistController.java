package com.amin.dev.artists;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/manager/artists")
@AllArgsConstructor
public class ArtistController {
    private final ArtistService service;

    @PostMapping
    public Artist registerArtist(@RequestBody ArtistRequest artistRequest) {
        log.info("Manage Artist : " + artistRequest.firstName() + " - " + artistRequest.lastName());
        return service.addArtist(artistRequest);
    }
}
