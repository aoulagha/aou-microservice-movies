package com.amin.dev.movies;

import com.amin.dev.clients.artists.ArtistRequest;

import java.util.List;

public record MovieArtistsRequest(
        List<ArtistRequest> artists,
        String originalName,
        String frenchName,
        Integer yearRelease) {

    public MovieArtistsRequest(List<ArtistRequest> artists, String originalName, String frenchName, Integer yearRelease) {
        this.artists = artists;
        this.originalName = originalName;
        this.frenchName = frenchName;
        this.yearRelease = yearRelease;
    }
}
