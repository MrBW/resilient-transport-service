package de.codecentric.resilient.booking.config;

import com.netflix.config.ConcurrentMapConfiguration;
import org.apache.commons.configuration.AbstractConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Benjamin Wilms (xd98870)
 */
@Configuration
public class ArchaiusConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArchaiusConfiguration.class);
    @Bean
    public AbstractConfiguration sampleArchaiusConfiguration() throws Exception {

        LOGGER.info("Enable Archaius Configuration");
        ConcurrentMapConfiguration concurrentMapConfiguration = new ConcurrentMapConfiguration();

        return concurrentMapConfiguration;
    }
}
