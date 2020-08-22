package org.robert.core.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.robert.core.exception.GlobalExceptionHandler;
import org.robert.core.filter.XssFilter;
import org.robert.core.util.SnowflakeIdWorker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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
