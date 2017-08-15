package de.codecentric.resilient.configuration;

import com.netflix.config.ConcurrentMapConfiguration;
import org.apache.commons.configuration.AbstractConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * @author Benjamin Wilms (xd98870)
 */
@Configuration
@EnableScheduling
public class DefautConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefautConfiguration.class);

    @RefreshScope
    @Bean
    public AbstractConfiguration sampleArchaiusConfiguration() throws Exception {

        LOGGER.info("Enable Archaius Configuration");
        ConcurrentMapConfiguration concurrentMapConfiguration = new ConcurrentMapConfiguration();

        return concurrentMapConfiguration;
    }

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    RefreshConfigScheduler refreshConfigScheduler() {
//        return new RefreshConfigScheduler();
//    }


}
