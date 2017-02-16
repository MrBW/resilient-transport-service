package com.codecentric.de.resilient.connote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import com.codecentric.de.resilient.configuration.ArchaiusConfiguration;

/**
 * Connote Service
 * @author Benjamin Wilms
 */
@EnableDiscoveryClient
@SpringBootApplication
@Import(ArchaiusConfiguration.class)
@EntityScan(basePackageClasses = {ConnoteServiceApplication.class, Jsr310JpaConverters.class})
public class ConnoteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConnoteServiceApplication.class, args);
    }
}
