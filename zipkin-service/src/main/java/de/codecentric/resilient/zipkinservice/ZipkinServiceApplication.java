package de.codecentric.resilient.zipkinservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import zipkin.server.EnableZipkinServer;

/**
 * @author Benjamin Wilms
 */

@EnableZipkinServer
@EnableDiscoveryClient
@SpringBootApplication
public class ZipkinServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZipkinServiceApplication.class, args);
    }
}
