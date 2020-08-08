package org.robert.redis.config;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Iterator;
import java.util.Objects;

@Configuration
public class RedisConfig {
    private static final Logger log = LoggerFactory.getLogger(RedisConfig.class);
    @Autowired
    private RedisConfigBean redisConfigBean;
    @Autowired
    private RefreshScope refreshScope;

    @Bean
    public RedisConfigBean getRedisConfigBean() {
        return new RedisConfigBean();
    }

    @Bean({"redisConnectionFactory"})
    public RedisConnectionFactory getConnectionFactory() {
        log.info("redis config node: {}", this.redisConfigBean.getNodesInfoList());

        int nodeSize = this.redisConfigBean.getNodesInfoList().size();
        String password = this.redisConfigBean.getPassword();
        if (nodeSize == 1) {
            RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
            RedisNode redisNode = (RedisNode) this.redisConfigBean.getNodesInfoList().get(0);
            configuration.setHostName((String) Objects.requireNonNull(redisNode.getHost()));
            configuration.setPort((Integer) Objects.requireNonNull(redisNode.getPort()));
            configuration.setDatabase(0);
            if (!StringUtils.isEmpty(password)) {
                configuration.setPassword(RedisPassword.of(password));
            }

            return new LettuceConnectionFactory(configuration);
        } else {
            RedisClusterConfiguration configuration = new RedisClusterConfiguration();
            configuration.setClusterNodes(this.redisConfigBean.getNodesInfoList());
            configuration.setMaxRedirects(this.redisConfigBean.getNodesInfoList().size());
            if (!StringUtils.isEmpty(password)) {
                configuration.setPassword(RedisPassword.of(password));
            }

            return new LettuceConnectionFactory(configuration);
        }
    }

    @Bean(name = {"redisTemplate"})
    public RedisTemplate<String, Object> getRedisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate();
        template.setConnectionFactory(this.getConnectionFactory());
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
        om.enableDefaultTyping(DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setKeySerializer(jackson2JsonRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @ApolloConfigChangeListener({"redis.yml"})
    public void onChange(ConfigChangeEvent changeEvent) {
        boolean isChanged = false;
        Iterator var3 = changeEvent.changedKeys().iterator();

        while (var3.hasNext()) {
            String changeKey = (String) var3.next();
            if (changeKey.startsWith("redis")) {
                isChanged = true;
                break;
            }
        }

        if (isChanged) {
            this.refreshScope.refresh("redisConfigBean");
        }
    }

    @Bean
    public CacheManager cacheManager() {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(this.getConnectionFactory());
        RedisSerializer<Object> jsonSerializer = new GenericJackson2JsonRedisSerializer();
        SerializationPair<Object> pair = SerializationPair.fromSerializer(jsonSerializer);
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair).entryTtl(Duration.ofSeconds(this.redisConfigBean.getCacheExpireTime() != null ? (long) this.redisConfigBean.getCacheExpireTime() : 60L));
        return RedisCacheManager.builder(redisCacheWriter).cacheDefaults(redisCacheConfiguration).build();
    }
}

