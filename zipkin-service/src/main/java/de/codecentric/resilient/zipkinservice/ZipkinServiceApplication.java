package de.codecentric.resilient.zipkinservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import zipkin.server.EnableZipkinServer;

/**
 * @author Benjamin Wilms
 */

@EnableZipkinServer
@EnableAutoConfiguration
@RefreshScope
@EnableDiscoveryClient
@SpringBootApplication
public class ZipkinServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZipkinServiceApplication.class, args);
    }
}
