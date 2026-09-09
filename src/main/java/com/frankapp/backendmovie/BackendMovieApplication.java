package com.frankapp.backendmovie;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@OpenAPIDefinition(
        info = @Info(
                title = "Backend Movie API",
                version = "1.0",
                description = "API for managing movies",
                contact = @Contact(
                        name = "Francis C. Chigozie",
                        email = "chigozie-cyriacus.francis@iu-study.org",
                        url = "https://www.franciswebapp.com"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "External documentation for Backend Movie API",
                url = "https://github.com/IUCampus/movie-course-backend-deployment/blob/main/README.md"

        )
)
@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.frankapp.backendmovie.repository",
        includeFilters = @org.springframework.context.annotation.ComponentScan.Filter(
                type = org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE,
                classes = {
                        com.frankapp.backendmovie.repository.UserRepository.class,
                        com.frankapp.backendmovie.repository.PaymentRepository.class,
                        com.frankapp.backendmovie.repository.ReviewRepository.class,
                        com.frankapp.backendmovie.repository.BookingRepository.class
                }))
@EnableReactiveMongoRepositories(basePackages = "com.frankapp.backendmovie.repository",
        includeFilters = @org.springframework.context.annotation.ComponentScan.Filter(
                type = org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE,
                classes = {
                        com.frankapp.backendmovie.repository.MovieRepository.class
                }))
public class BackendMovieApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendMovieApplication.class, args);
    }

}
