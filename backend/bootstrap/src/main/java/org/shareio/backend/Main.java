package org.shareio.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScans({
        @ComponentScan(basePackages = "org.shareio.backend.infrastructure.controller")
})
public class Main {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Main.class);
        // ... customize application settings here
        application.run(args);
    }
}
