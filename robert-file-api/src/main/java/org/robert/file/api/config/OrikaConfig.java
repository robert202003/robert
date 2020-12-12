package org.robert.user.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrikaConfig {

    public OrikaConfig() {
    }

    @Bean
    public OrikaBeanMapper beanMapper() {
        return new OrikaBeanMapper();
    }
}
