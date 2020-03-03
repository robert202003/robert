package com.github.cloud.gateway.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * properties配置类
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "robot.security")
@RefreshScope
public class SecurityProperties {

    private SignIgnoresProperties ignoresSign = new SignIgnoresProperties();

}
