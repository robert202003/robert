package org.robert.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import tk.mybatis.spring.annotation.MapperScan;

@EnableDiscoveryClient
@ComponentScan(basePackages = {"org.robert.*"})
@EnableFeignClients(basePackages = {"org.robert.*"})
@SpringBootApplication
public class UserApiApplication {
    public static void main(String[] args) {

        SpringApplication.run(UserApiApplication.class, args);
    }
}
