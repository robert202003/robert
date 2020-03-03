package com.github.cloud.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableAdminServer
@EnableDiscoveryClient
@EnableWebSecurity
public class MonitorApplication {
    public static void main(String[] args) {

        SpringApplication.run(MonitorApplication.class, args);
    }

}
