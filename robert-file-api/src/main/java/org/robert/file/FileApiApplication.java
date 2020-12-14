package org.robert.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"org.robert.*"})
public class FileApiApplication {
    public static void main(String[] args) {

        SpringApplication.run(FileApiApplication.class, args);
    }
}
