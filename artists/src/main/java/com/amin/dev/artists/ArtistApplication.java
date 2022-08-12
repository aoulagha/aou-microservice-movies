package com.amin.dev.artists;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {"com.amin.dev.artists"
        }
)
public class ArtistApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArtistApplication.class);
    }
}
