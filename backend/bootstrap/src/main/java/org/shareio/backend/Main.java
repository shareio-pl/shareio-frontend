package org.shareio.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class Main {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Main.class);
        // ... customize application settings here
        application.run(args);
    }
}
