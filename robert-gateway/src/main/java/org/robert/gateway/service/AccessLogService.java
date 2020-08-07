package org.robert.gateway.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.cloud.core.constant.SecurityConstants;
import com.github.cloud.core.util.DateUtils;
import com.github.cloud.core.util.JwtUtils;
import com.github.cloud.gateway.context.GatewayContext;
import com.github.cloud.gateway.util.ReactiveWebUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;

import java.util.*;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

@Slf4j
@Component
public class AccessLogService {

    private static Joiner joiner = Joiner.on("");

    @Value("${spring.application.name}")
    private String defaultServiceId;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @JsonIgnore
    private Set<String> ignores = new HashSet<>(Arrays.asList(new String[]{
            "/**/oauth/check_token/**",
            "/**/gateway/access/logs/**",
            "/webjars/**"

    }));

    /**
     * 不记录日志
     *
     * @param requestPath
     * @return
     */
    public boolean ignore(String requestPath) {
        Iterator<String> iterator = ignores.iterator();
        while (iterator.hasNext()) {
            String path = iterator.next();
            if (antPathMatcher.match(path, requestPath)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 记录报文信息到mq
     *
     * @param exchange
     * @param ex
     * @param responseList
     */
    public void sendLog(ServerWebExchange exchange, Exception ex, List<String> responseList) {

       /* List<String> data = new ArrayList<>();
        for (String s : responseList) {
            data.add(s.replaceAll("\t|\n",""));
        }*/
        String responseData = joiner.join(responseList);
        try {
            Map message = getMessageData(exchange, ex);
            Map requestMessage = (Map) message.get("requestMessage");
            Map responseMessage = (Map) message.get("responseMessage");
            responseMessage.put("responseBody", responseData);
            log.info(requestMessage.toString());
           // log.info(JSON.toJSONString(responseMessage));
            //log.info(requestMessage.toString());
            log.info(responseMessage.toString());

        } catch (Exception e) {
            log.error("access logs save error:{}", e);
        }
    }

    /**
     * 网关发生异常时调用
     *
     * @param exchange
     * @param ex
     */
    public void sendLog(ServerWebExchange exchange, Exception ex,String responseBody) {
        try {
            Map message = getMessageData(exchange, ex);
            Map responseMessage = (Map) message.get("responseMessage");
            Map requestMessage = (Map) message.get("requestMessage");
            responseMessage.put("responseBody", responseBody);
            log.info(requestMessage.toString());
            log.error(responseMessage.toString());
        } catch (Exception e) {
            log.error("access logs save error:{}", e);
        }
    }


    /**
     * 获取报文信息
     *
     * @param exchange
     * @param ex
     * @return
     */
    private Map<String, LinkedHashMap> getMessageData(ServerWebExchange exchange, Exception ex) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        Map data = Maps.newHashMap();
        Map requestMap = Maps.newLinkedHashMap();
        Map responseMap = Maps.newLinkedHashMap();
        Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
        Map userMap = Maps.newHashMap();
        try {
            String authorization = request.getHeaders().getFirst(SecurityConstants.AUTHORIZATION);
            if (authorization != null && authorization.startsWith(SecurityConstants.BEARER_TYPE)) {
                String token = authorization.substring(SecurityConstants.BEARER_TYPE.length()).trim();
                JSONObject userJson = JwtUtils.decodeAndVerify(token);
                Long userId = userJson.getLong("userId");
                String userName = userJson.getString("userName");
                String userType = userJson.getString("userType");
                String appId = userJson.getString("appId");
                Long orgId = userJson.getLong("orgId");
                userMap.put("appId", appId);
                userMap.put("orgId", orgId);
                userMap.put("userId", userId);
                userMap.put("userName", userName);
                userMap.put("userType", userType);

            }
        } catch (Exception e) {
            log.error("decode token  error:{}", e);
        }

        try {
            String serviceId = null;
            if (route != null) {
                serviceId = route.getUri().toString().replace("lb://", "");
            }
            int httpStatus = response.getStatusCode().value();
            String requestPath = request.getURI().getPath();
            Map<String, String> requestHeaders = request.getHeaders().toSingleValueMap();
            String requestId = requestHeaders.get("X-Request-Id");
            //请求报文
            String method = request.getMethodValue();
            Map requestParam = Maps.newHashMap();
            GatewayContext gatewayContext = exchange.getAttribute(GatewayContext.CACHE_GATEWAY_CONTEXT);
            if (gatewayContext != null) {
                requestParam = gatewayContext.getAllRequestData().toSingleValueMap();
            }
            if (antPathMatcher.match("/api/user/login", requestPath)) {
                //不要打印
                requestParam.remove("password");
                requestParam.remove("client_secret");
            }

            String userAgent = requestHeaders.get(HttpHeaders.USER_AGENT);
            String ip = ReactiveWebUtils.getRemoteAddress(exchange);
            Object requestTime = exchange.getAttribute("requestTime");

            requestMap.put("requestUrl", requestPath);
            requestMap.put("requestId", requestId);
            requestMap.put("requestTime", requestTime);
            requestMap.put("userInfo", jsonFormat(JSON.toJSONString(userMap)));
            requestMap.put("requestHeaders", jsonFormat(JSON.toJSONString(requestHeaders)));
            requestMap.put("serviceId", serviceId == null ? defaultServiceId : serviceId);
            requestMap.put("params", jsonFormat(JSON.toJSONString(requestParam)));
            requestMap.put("ip", ip);
            requestMap.put("userAgent", userAgent);
            requestMap.put("method", method);

            //响应报文
            Map<String, String> headers = response.getHeaders().toSingleValueMap();
            responseMap.put("responseUrl", requestPath);
            responseMap.put("requestId", requestId);
            responseMap.put("responseTime", DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:sss"));
            responseMap.put("userInfo", jsonFormat(JSON.toJSONString(userMap)));
            responseMap.put("responseHeaders", jsonFormat(JSON.toJSONString(headers)));
            responseMap.put("serviceId", serviceId == null ? defaultServiceId : serviceId);
            responseMap.put("httpStatus", httpStatus);

            data.put("requestMessage", requestMap);
            data.put("responseMessage", responseMap);

        } catch (Exception e) {
            log.error("access logs save error:{}", e);
        }
        return data;
    }

    public  String jsonFormat(String jsonString) {
        JSONObject object= JSONObject.parseObject(jsonString);
        jsonString = JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
        return jsonString;
    }
}
