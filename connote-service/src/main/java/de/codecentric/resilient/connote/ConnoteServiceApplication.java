package de.codecentric.resilient.connote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import de.codecentric.resilient.configuration.ArchaiusConfiguration;

/**
 * Connote Service
 * @author Benjamin Wilms
 */
@EnableDiscoveryClient
@EnableAutoConfiguration
@RefreshScope
@SpringBootApplication
@Import(ArchaiusConfiguration.class)
@EntityScan(basePackageClasses = {ConnoteServiceApplication.class, Jsr310JpaConverters.class})
public class ConnoteServiceApplication {

    @Bean
    public Sampler sampler(){
        return new AlwaysSampler();
    }

    public static void main(String[] args) {
        SpringApplication.run(ConnoteServiceApplication.class, args);
    }
}
