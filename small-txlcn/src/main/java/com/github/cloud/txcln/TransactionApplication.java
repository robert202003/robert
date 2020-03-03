package com.github.cloud.txcln;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * txlcn分布式事务管理
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagerServer
public class TransactionApplication {

    public static void main(String[] args) {

        SpringApplication.run(TransactionApplication.class, args);
    }
}
