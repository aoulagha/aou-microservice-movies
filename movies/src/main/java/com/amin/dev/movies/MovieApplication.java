package com.amin.dev.movies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {"com.amin.dev.movies"
        }
)

public class MovieApplication {

    public static void main(String[] args) {

        SpringApplication.run(MovieApplication.class, args);
    }
}
