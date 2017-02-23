package com.codecentric.de.resilient.transport.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import com.codecentric.de.resilient.configuration.ArchaiusConfiguration;
import com.codecentric.de.resilient.transport.api.gateway.config.Configuration;

/**
 * Connote Service
 * @author Benjamin Wilms
 */
@EnableDiscoveryClient
@SpringBootApplication
@Import(value = {Configuration.class, ArchaiusConfiguration.class})
@EntityScan(basePackageClasses = {TransportApiGatewayApplication.class, Jsr310JpaConverters.class})
public class TransportApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransportApiGatewayApplication.class, args);
    }
}
