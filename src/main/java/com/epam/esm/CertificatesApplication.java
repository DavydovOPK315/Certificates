package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Class to start the project
 *
 * @author Denis Davydov
 * @version 2.0
 */
@SpringBootApplication
@EnableJpaAuditing
public class CertificatesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CertificatesApplication.class, args);
    }
}