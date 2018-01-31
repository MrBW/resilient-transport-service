package de.codecentric.resilient.booking;

import de.codecentric.resilient.configuration.DefautConfiguration;
import de.mrbw.chaos.monkey.EnableChaosMonkey;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * Connote Service
 *
 * @author Benjamin Wilms
 */
@EnableChaosMonkey
@EnableDiscoveryClient
@EnableAutoConfiguration
@RefreshScope
@SpringBootApplication
@EnableCircuitBreaker
@Import(DefautConfiguration.class)
@EntityScan(basePackageClasses = {BookingServiceApplication.class, Jsr310JpaConverters.class})
public class BookingServiceApplication {

    @Bean
    public Sampler sampler() {
        return new AlwaysSampler();
    }

    public static void main(String[] args) {
        SpringApplication.run(BookingServiceApplication.class, args);
    }
}
