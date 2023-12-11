package com.effectivemobile.testproject;

import com.effectivemobile.testproject.auth.AuthRequest;
import com.effectivemobile.testproject.auth.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TestProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestProjectApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthService authService
    ) {
        return args -> {
            AuthRequest userRequest = AuthRequest.builder()
                    .email("mail@mail.com")
                    .password("123456")
                    .build();
            System.out.println("AUTHORIZATION TOKEN: " + authService.authenticate(userRequest).getToken());
        };
    }
}
