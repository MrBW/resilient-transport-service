package de.codecentric.resilient.address;

import com.netflix.config.DynamicBooleanProperty;
import de.codecentric.resilient.chaosmonkey.ChaosMonkey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import com.netflix.config.DynamicPropertyFactory;
import de.codecentric.resilient.address.entity.Address;
import de.codecentric.resilient.address.repository.AddressRepository;
import de.codecentric.resilient.configuration.ArchaiusConfiguration;

/**
 * @author Benjamin Wilms
 */
@EnableDiscoveryClient
@SpringBootApplication
@Import(ArchaiusConfiguration.class)
@EntityScan(basePackageClasses = {AddressServiceApplication.class, Jsr310JpaConverters.class})
public class AddressServiceApplication {

    @Bean
    public Sampler sampler() {
        return new AlwaysSampler();
    }

    private static final Logger log = LoggerFactory.getLogger(AddressServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AddressServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(AddressRepository repository) {
        return (args) -> {
            repository.save(new Address("DE", "Solingen", "42697", "Hochstraße", "11"));
            repository.save(new Address("DE", "Berlin", "10785", "Kemperplatz", "1"));
            repository.save(new Address("DE", "Dortmund", "44137", "Hoher Wall", "15"));
            repository.save(new Address("DE", "Düsseldorf", "40591", "Kölner Landstraße", "11"));
            repository.save(new Address("DE", "Frankfurt", "60486", "Kreuznacher Straße", "30"));
            repository.save(new Address("DE", "Hamburg", "22767", "Große Elbstraße", "14"));
            repository.save(new Address("DE", "Karlsruhe", "76135", "Gartenstraße", "69a"));
            repository.save(new Address("DE", "München", "80687", "Elsenheimerstraße", "55a"));
            repository.save(new Address("DE", "Münster", "48167", "Wolbecker Windmühle", "29j"));
            repository.save(new Address("DE", "Nürnberg", "90403", "Josephsplatz", "8"));
            repository.save(new Address("DE", "Stuttgart", "70563", "Curiesstraße", "2"));

            log.info("### Address count:" + repository.count());

        };
    }
}
