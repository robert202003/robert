package org.robert.gateway.exception;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.util.function.Supplier;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.cloud.core.util.MessageUtils;
import com.github.cloud.gateway.service.AccessLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Slf4j
public class JsonSentinelGatewayBlockExceptionHandler implements WebExceptionHandler {
    private static final String ACCEPT_LANGUAGE = "Accept-Language";
    @Autowired
    private AccessLogService accessLogService;

    private Mono<Void> writeResponse(ServerResponse response, ServerWebExchange exchange, Throwable ex) {
        ServerHttpRequest request = exchange.getRequest();
        if (ex instanceof FlowException) {
            FlowException fe = (FlowException) ex;
            FlowRule rule = fe.getRule();
            Map<String, String> requestHeaders = request.getHeaders().toSingleValueMap();
            String requestId = requestHeaders.get("X-Request-Id");
            log.error("requestId={}; FlowException={}",requestId,jsonFormat(JSON.toJSONString(rule)));
        }
        HttpHeaders headers = request.getHeaders();
        List<String> acceptLanguages = headers.get(ACCEPT_LANGUAGE);
        String message = "";
        if (acceptLanguages != null && acceptLanguages.size() > 0) {
            String acceptLanguage = acceptLanguages.get(0);
            message = MessageUtils.get("robot.sentinel.global.error", acceptLanguage);
        }
        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        String responseBody = String.format("{\"code\": 403,\"success\": false,\"errorDetail\":\"%s\",\"message\":\"%s\"}", "Blocked by Sentinel: FlowException", message);
        accessLogService.sendLog(exchange, null, jsonFormat(responseBody));
        byte[] msgByte = responseBody.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = serverHttpResponse.bufferFactory().wrap(msgByte);
        return serverHttpResponse.writeWith(Mono.just(buffer));
    }

    private List<ViewResolver> viewResolvers;
    private List<HttpMessageWriter<?>> messageWriters;

    public JsonSentinelGatewayBlockExceptionHandler(List<ViewResolver> viewResolvers, ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolvers;
        this.messageWriters = serverCodecConfigurer.getWriters();
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }
        // This exception handler only handles rejection by Sentinel.
        if (!BlockException.isBlockException(ex)) {
            return Mono.error(ex);
        }
        return handleBlockedRequest(exchange, ex)
                .flatMap(response -> writeResponse(response, exchange, ex));
    }

    private Mono<ServerResponse> handleBlockedRequest(ServerWebExchange exchange, Throwable throwable) {
        return GatewayCallbackManager.getBlockHandler().handleRequest(exchange, throwable);
    }

    private final Supplier<ServerResponse.Context> contextSupplier = () -> new ServerResponse.Context() {
        @Override
        public List<HttpMessageWriter<?>> messageWriters() {
            return JsonSentinelGatewayBlockExceptionHandler.this.messageWriters;
        }

        @Override
        public List<ViewResolver> viewResolvers() {
            return JsonSentinelGatewayBlockExceptionHandler.this.viewResolvers;
        }
    };

    public  String jsonFormat(String jsonString) {
        JSONObject object= JSONObject.parseObject(jsonString);
        jsonString = JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
        return jsonString;
    }
}
