package com.codecentric.de.resilient.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import com.codecentric.de.resilient.customer.entity.Customer;
import com.codecentric.de.resilient.customer.repository.CustomerRepository;
import com.codecentric.de.resilient.configuration.ArchaiusConfiguration;

/**
 * @author Benjamin Wilms
 */
@EnableDiscoveryClient
@SpringBootApplication
@Import(ArchaiusConfiguration.class)
@EntityScan(basePackageClasses = {CustomerServiceApplication.class, Jsr310JpaConverters.class})
public class CustomerServiceApplication {
    private static final Logger log = LoggerFactory.getLogger(CustomerServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(CustomerRepository repository) {
        return (args) -> {
            repository.save(new Customer("Meier"));
            repository.save(new Customer("Fritz"));
            repository.save(new Customer("Walters"));
            repository.save(new Customer("Schneider"));
            repository.save(new Customer("Schmidt"));
            repository.save(new Customer("Ludewig"));

            log.info("### Customer count:" + repository.count());
        };
    }
}
