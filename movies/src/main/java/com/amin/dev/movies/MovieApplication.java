package com.amin.dev.movies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication(
        scanBasePackages = {"com.amin.dev.movies",
                "com.amin.dev.amqp"
        }
)
@EnableEurekaClient
@EnableFeignClients(
        basePackages = "com.amin.dev" +
                ".clients"
)
@PropertySources({
        @PropertySource("classpath:clients-${spring.profiles.active}.properties")
})
public class MovieApplication {

    public static void main(String[] args) {

        SpringApplication.run(MovieApplication.class, args);
    }
}
