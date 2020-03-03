package com.github.cloud.auth.client.config;

import com.github.cloud.auth.client.store.AuthDbTokenStore;
import com.github.cloud.auth.client.store.AuthJwtTokenStore;
import com.github.cloud.auth.client.store.ResourceJwtTokenStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * token存储配置
 *
 */
@Configuration
public class TokenStoreConfig {

    @Configuration
    @ConditionalOnProperty(prefix = "robot.oauth2.token.store", name = "type", havingValue = "db")
    @Import(AuthDbTokenStore.class)
    public class JdbcTokenConfig {
    }

    @Configuration
    @ConditionalOnProperty(prefix = "robot.oauth2.token.store", name = "type", havingValue = "authJwt")
    @Import(AuthJwtTokenStore.class)
    public class AuthJwtTokenConfig {
    }

    /**
     * 资源服务器 TokenStore
     */
    @Configuration
    @ConditionalOnProperty(prefix = "robot.oauth2.token.store", name = "type", havingValue = "resJwt")
    @Import(ResourceJwtTokenStore.class)
    public class ResJwtTokenConfig {
    }

}
