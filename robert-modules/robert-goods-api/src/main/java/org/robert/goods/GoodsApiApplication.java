package org.robert;

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
public class GoodsApiApplication {
    public static void main(String[] args) {

        SpringApplication.run(GoodsApiApplication.class, args);
    }
}
