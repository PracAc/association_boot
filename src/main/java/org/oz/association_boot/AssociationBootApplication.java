package org.oz.association_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AssociationBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssociationBootApplication.class, args);
    }

}
