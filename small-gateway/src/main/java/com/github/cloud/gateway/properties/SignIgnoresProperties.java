package com.github.cloud.gateway.properties;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 不需要签名的url配置
 */
@Setter
@Getter
public class SignIgnoresProperties {


    /**
     * 必定忽略的
     */
    private static final String[] ENDPOINTS = {
            "/oauth/**",
            "/actuator/**",
            "/druid/**",
            "/error/**",
            "/favicon.ico",
            "/druid/**"
    };

    /**
     * 设置不用签名的的url
     */
    private String[] httpUrl = {};

    public String[] getHttpUrl() {
        if (httpUrl == null || httpUrl.length == 0) {
            return ENDPOINTS;
        }
        List<String> list = new ArrayList<>();
        for (String url : ENDPOINTS) {
            list.add(url);
        }
        for (String url : httpUrl) {
            list.add(url);
        }
        return list.toArray(new String[list.size()]);
    }
}
