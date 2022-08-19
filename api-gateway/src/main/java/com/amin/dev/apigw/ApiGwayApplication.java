package com.amin.dev.apigw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ApiGwayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGwayApplication.class);
    }
}
