package com.github.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.github.cloud.goods.mapper")
@EnableFeignClients
//@EnableDistributedTransaction
public class GoodsApplication {
    public static void main(String[] args) {

        SpringApplication.run(GoodsApplication.class, args);
    }
}
