package org.robert.auth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(AuthServerApplication.class, args);
    }
}
