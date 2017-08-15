package de.codecentric.resilient.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.cloud.endpoint.RefreshEndpoint;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Benjamin Wilms (xd98870)
 */
@Component
@EnableScheduling
public class RefreshConfigScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RefreshConfigScheduler.class);


    private ContextRefresher refreshEndpoint;

    @Scheduled(fixedRate = 10000)
    public void refreshConfig() {
        LOGGER.error("REFRESH CONFIG");
        refreshEndpoint.refresh();

    }
}
