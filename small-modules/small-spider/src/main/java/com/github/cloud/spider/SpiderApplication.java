package com.github.cloud.spider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.github.cloud.spider.mapper")
@EnableCaching
public class SpiderApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpiderApplication.class, args);
	}

}
