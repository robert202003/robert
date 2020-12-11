package org.robert.goods;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@EnableDiscoveryClient
@EnableTransactionManagement
@EnableAsync
@EnableFeignClients(basePackages = {"org.robert.*"})
//@EnableApolloConfig(value={"db.yml", "common.bootstrap"})
@SpringBootApplication(scanBasePackages={"org.robert.*"},exclude = {DataSourceAutoConfiguration.class})
public class GoodsApiApplication {

    public static void main(String[] args) {

        SpringApplication.run(GoodsApiApplication.class, args);
    }
}
