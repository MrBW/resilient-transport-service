package de.codecentric.resilient.customer;

import de.codecentric.resilient.customer.entity.Customer;
import de.codecentric.resilient.customer.repository.CustomerRepository;
import de.mrbw.chaos.monkey.EnableChaosMonkey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
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
import de.codecentric.resilient.configuration.DefautConfiguration;


/**
 * @author Benjamin Wilms
 */
@EnableChaosMonkey
@EnableAutoConfiguration
@RefreshScope
@EnableDiscoveryClient
@SpringBootApplication
@Import(DefautConfiguration.class)
@EntityScan(basePackageClasses = {CustomerServiceApplication.class, Jsr310JpaConverters.class})
public class CustomerServiceApplication {

    @Bean
    public Sampler sampler(){
        return new AlwaysSampler();
    }
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
