package com.amin.dev.clients.artists;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "artist",
        url = "${clients.artist.url}"
)
public interface ArtistClient {

    @PostMapping(path = "/api/manager/artists")
    public Integer registerArtist(@RequestBody ArtistRequest artistRequest);
}
