package org.robert.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.github.cloud.core.constant.RedisConstant;
import com.github.cloud.core.constant.SecurityConstants;
import com.github.cloud.core.exception.ApiException;
import com.github.cloud.core.exception.SignatureException;
import com.github.cloud.core.util.JwtUtils;
import com.github.cloud.gateway.context.GatewayContext;
import com.github.cloud.gateway.properties.SecurityProperties;
import com.github.cloud.gateway.properties.SignIgnoresProperties;
import com.github.cloud.gateway.util.SignatureUtils;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 数字验签前置过滤器
 */
@Component
public class PreSignatureFilter implements WebFilter {

    private static final AntPathMatcher pathMatch = new AntPathMatcher();

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String requestPath = request.getPath().value();

        SignIgnoresProperties ignoresProperties = securityProperties.getIgnoresSign();
        String[] signIgnores = ignoresProperties.getHttpUrl();
        for (String path : signIgnores) {
            if (pathMatch.match(path, requestPath)) {
                return chain.filter(exchange);
            }
        }
        try {
            String authorization = request.getHeaders().getFirst(SecurityConstants.AUTHORIZATION);
            if (authorization == null || !authorization.startsWith(SecurityConstants.BEARER_TYPE)) {
                throw new ApiException(401, "请求头中token信息为空");
            }
            String token = authorization.substring(SecurityConstants.BEARER_TYPE.length()).trim();
            if (token == null) {
                throw new ApiException(401, "token不能为空");
            }
            JSONObject jsonObject = JwtUtils.decodeAndVerify(token);
            if (jsonObject == null) {
                throw new ApiException(401, "token不正确");
            }

            boolean checkExp = JwtUtils.checkExp(jsonObject);
            if (!checkExp) {
                throw new ApiException(403, "token已经过期");
            }
            String userId = jsonObject.getString(SecurityConstants.USER_ID);
            String redisToken = redisTemplate.opsForValue().get(RedisConstant.USER_TOKEN + userId);
            if (redisToken == null || (!token.equals(redisToken))) {
                throw new ApiException(401, "token不正确或者已经过期");
            }
            String secret_key = jsonObject.getString(SecurityConstants.CLIENT_SECRET);
            String app_id = jsonObject.getString(SecurityConstants.CLIENT_ID);
            Map params = Maps.newHashMap();
            GatewayContext gatewayContext = exchange.getAttribute(GatewayContext.CACHE_GATEWAY_CONTEXT);
            if (gatewayContext != null) {
                params = gatewayContext.getAllRequestData().toSingleValueMap();
            }
            params.put(SecurityConstants.APP_ID, app_id);
            // 验证请求参数
            SignatureUtils.validateParams(params);
            //开始验证签名
            if (!SignatureUtils.validateSign(params, secret_key)) {
                throw new SignatureException("验签失败");
            }
        } catch (Exception ex) {
            if (ex instanceof SignatureException) {
                throw new SignatureException(ex.getMessage());
            } else if(ex instanceof ApiException) {
                ApiException apiException = (ApiException)ex;
                throw new ApiException(apiException.getStatusCode(), apiException.getMessage());
            } else {
                throw new ApiException(401, ex.getMessage());
            }

        }
        return chain.filter(exchange);
    }

}
