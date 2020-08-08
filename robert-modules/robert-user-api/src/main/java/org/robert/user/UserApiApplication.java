package org.robert.user;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@EnableDiscoveryClient
@EnableTransactionManagement
@ComponentScan(basePackages = {"org.robert.*"})
@MapperScan("org.robert.goods.api.mapper")
@EnableFeignClients(basePackages = {"org.robert.*"})
@EnableApolloConfig(value={"db.yml", "common.bootstrap"})
@SpringBootApplication
public class UserApiApplication {
    public static void main(String[] args) {

        SpringApplication.run(UserApiApplication.class, args);
    }
}
