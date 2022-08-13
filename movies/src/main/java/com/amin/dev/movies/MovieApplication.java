package com.amin.dev.movies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(
        scanBasePackages = {"com.amin.dev.movies"
        }
)
@EnableEurekaClient
public class MovieApplication {

    public static void main(String[] args) {

        SpringApplication.run(MovieApplication.class, args);
    }
}
