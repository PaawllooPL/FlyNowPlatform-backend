package com.flynow.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@SpringBootApplication
//@ComponentScan(basePackages = {"com.flynow.*"})
@EntityScan(basePackages = {"com.flynow.repository.entities"})
@EnableJpaRepositories(basePackages = {"com.flynow.repository.repositories"})
public class FlynowApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlynowApplication.class, args);
    }
}
