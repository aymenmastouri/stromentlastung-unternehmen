package de.stromentlastung.unternehmen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Unternehmensregister: Stammdaten, Zuständigkeit, Zuordnung nach § 15 StromStV. */
@SpringBootApplication
public class UnternehmenApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnternehmenApplication.class, args);
    }
}
