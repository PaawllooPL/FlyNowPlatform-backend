package com.flynow.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"com.flynow.api.config.beans", "com.flynow"})
@EntityScan(basePackages = {"com.flynow.repository.entities"})
@EnableJpaRepositories(basePackages = {"com.flynow.repository.repositories.jpa"})
@SpringBootApplication
public class FlynowApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlynowApplication.class, args);

    }
}