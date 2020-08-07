package org.robert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagement
@EnableCircuitBreaker
@MapperScan("com.github.cloud.user.mapper")
@EnableFeignClients
public class UserApiApplication {
    public static void main(String[] args) {

        SpringApplication.run(UserApiApplication.class, args);
    }
}
