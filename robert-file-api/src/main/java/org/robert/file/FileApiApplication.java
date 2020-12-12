package org.robert.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@ComponentScan(basePackages = {"org.robert.*"})
@SpringBootApplication
public class FileApiApplication {
    public static void main(String[] args) {

        SpringApplication.run(FileApiApplication.class, args);
    }
}
