package com.github.cloud.core.autoconfigure;

import com.github.cloud.core.exception.GlobalExceptionHandler;
import com.github.cloud.core.filter.XssFilter;
import com.github.cloud.core.util.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.alibaba.sentinel.annotation.SentinelRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class DefaultBeanAutoConfiguration {

    /**
     * xss过滤
     * body缓存
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean XssFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new XssFilter());
        log.info("XssFilter [{}]", filterRegistrationBean);
        return filterRegistrationBean;
    }

    /**
     * 统一异常处理配置
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(GlobalExceptionHandler.class)
    public GlobalExceptionHandler exceptionHandler() {
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();
        log.info("GlobalExceptionHandler [{}]", exceptionHandler);
        return exceptionHandler;
    }

    /**
     * ID生成器配置
     * @return
     */
    @Bean
    public SnowflakeIdWorker snowflakeIdWorker() {
        SnowflakeIdWorker snowflakeIdGenerator = new SnowflakeIdWorker(1,1);
        log.info("SnowflakeIdWorker [{}]", snowflakeIdGenerator);
        return snowflakeIdGenerator;
    }




}
