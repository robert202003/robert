package org.robert.redis.config;

import org.redisson.client.RedisException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "redis")
@RefreshScope
public class RedisConfigBean {

    private Integer cacheExpireTime;
    private Integer randomRange;
    private String password;
    private Integer timeoutInMillis;
    private Integer readTimeoutInMillis;
    private Integer dbIndex;
    private Integer maxTotal;
    private List<Map<String, String>> nodesInfo;

    public Integer getCacheExpireTime() {
        return cacheExpireTime;
    }

    public void setCacheExpireTime(Integer cacheExpireTime) {
        this.cacheExpireTime = cacheExpireTime;
    }

    public Integer getRandomRange() {
        return randomRange;
    }

    public void setRandomRange(Integer randomRange) {
        this.randomRange = randomRange;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTimeoutInMillis() {
        return timeoutInMillis;
    }

    public void setTimeoutInMillis(Integer timeoutInMillis) {
        this.timeoutInMillis = timeoutInMillis;
    }

    public Integer getReadTimeoutInMillis() {
        return readTimeoutInMillis;
    }

    public void setReadTimeoutInMillis(Integer readTimeoutInMillis) {
        this.readTimeoutInMillis = readTimeoutInMillis;
    }

    public Integer getDbIndex() {
        return dbIndex;
    }

    public void setDbIndex(Integer dbIndex) {
        this.dbIndex = dbIndex;
    }

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public List<Map<String, String>> getNodesInfo() {
        return nodesInfo;
    }

    public void setNodesInfo(List<Map<String, String>> nodesInfo) {
        this.nodesInfo = nodesInfo;
    }


    public List<RedisNode> getNodesInfoList() {
        if (CollectionUtils.isEmpty(this.nodesInfo)) {
            throw new RedisException("redis nodes is empty");
        } else {
            List<RedisNode> list = new ArrayList();
            this.nodesInfo.forEach((map) -> {
                list.add(new RedisNode((String)map.get("ip"), Integer.parseInt((String)map.get("port"))));
            });
            return list;
        }
    }


}
