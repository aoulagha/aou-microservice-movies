package com.amin.dev.role;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication(
        scanBasePackages = {"com.amin" +
                ".dev.role"
        }
)
@EnableEurekaClient
public class RoleApplication {
    public static void main(String[] args) {
        SpringApplication.run(RoleApplication.class);
    }
}
