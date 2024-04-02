package pl.strefakursow.security.oauth2.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ResourceServerApp {
    public static void main(String[] args) {
        SpringApplication.run(ResourceServerApp.class, args);
    }
}