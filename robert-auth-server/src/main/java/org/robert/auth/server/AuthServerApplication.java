package org.robert.auth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.github.cloud.auth.mapper"})
public class AuthServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(AuthServerApplication.class, args);
    }
}
