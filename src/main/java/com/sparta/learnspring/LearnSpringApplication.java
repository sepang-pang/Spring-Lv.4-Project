package com.sparta.learnspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LearnSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnSpringApplication.class, args);
    }

}
