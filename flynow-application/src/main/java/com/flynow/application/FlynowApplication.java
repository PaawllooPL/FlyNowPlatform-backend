package com.flynow.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {
        "com.flynow.application",
        "com.flynow.application.config",
        "com.flynow.api.config",
        "com.flynow.api.controllers",
        "com.flynow.infrastructure",
        "com.flynow.service",
})
@EntityScan(basePackages = {"com.flynow.infrastructure.entities"})
@EnableJpaRepositories(basePackages = {"com.flynow.infrastructure.repository.jpa"})
@SpringBootApplication
public class FlynowApplication {

    public static void main(String[] args) {
//        SpringApplication app = new SpringApplication(FlynowApplication.class);
//        app.run(args);
//        app.setEnvironmentPrefix("flynow");
        SpringApplication.run(FlynowApplication.class, args);
    }
}
