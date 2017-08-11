package de.codecentric.resilient.configuration;

import java.net.URI;

import de.codecentric.resilient.chaosmonkey.ChaosMonkey;
import org.boon.etcd.ClientBuilder;
import org.boon.etcd.Etcd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import com.netflix.config.*;
import com.netflix.config.source.EtcdConfigurationSource;
import org.springframework.context.annotation.Import;

/**
 * @author Benjamin Wilms (xd98870)
 */
@Configuration
public class ArchaiusConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArchaiusConfiguration.class);

    private DynamicStringProperty etcdServerPort =
        DynamicPropertyFactory.getInstance().getStringProperty("server.etcd.baseurl", "http://etcd:2379/v2/keys");

    public ArchaiusConfiguration() {

        // Config fallback (config.properties) and Etcd configuration
        ConcurrentCompositeConfiguration compositeConfig = new ConcurrentCompositeConfiguration();

        // File based configuration
        ClasspathPropertiesConfiguration.setPropertiesResourceRelativePath("config.properties");
        ClasspathPropertiesConfiguration.initialize();

        // CoresOS Etcd service configuration
        DynamicWatchedConfiguration etcdConfiguration = createEtcdConfiguration();

        if (etcdConfiguration != null)
            compositeConfig.addConfiguration(etcdConfiguration);

        ConfigurationManager.install(compositeConfig);

        LOGGER.debug(
            LOGGER.isDebugEnabled() ? "is Configuration installed: " + ConfigurationManager.isConfigurationInstalled() : null);

    }

    private DynamicWatchedConfiguration createEtcdConfiguration() {
        try {
            Etcd etcd = createEtcdClient();

            LOGGER.debug(LOGGER.isDebugEnabled() ? "Etcd Client created: " + (etcd != null) : null);

            EtcdConfigurationSource etcdConfigurationSource = new EtcdConfigurationSource(etcd, "/hystrix/");

            return new DynamicWatchedConfiguration(etcdConfigurationSource);

        } catch (Exception e) {
            LOGGER.error("CoresOS ETCD Service not reachable, Server: " + etcdServerPort, e);
            return null;
        }
    }

    /***
     * Create and initials etcd client
     * @return Etcd
     */
    private Etcd createEtcdClient() {
        LOGGER.debug(LOGGER.isDebugEnabled() ? "Etcd server baseurl: " + etcdServerPort.get() : null);

        ClientBuilder clientBuilder;
        clientBuilder = ClientBuilder.builder().hosts(URI.create(etcdServerPort.get())).timeOutInMilliseconds(5000);

        return clientBuilder.createClient();

    }

}
