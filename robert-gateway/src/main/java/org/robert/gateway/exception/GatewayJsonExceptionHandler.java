package org.robert.gateway.exception;

import lombok.extern.slf4j.Slf4j;
import org.robert.core.base.R;
import org.robert.core.exception.ApiException;
import org.robert.core.exception.SignatureException;
import org.robert.core.util.StringUtils;
import org.robert.gateway.service.AccessLogService;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * @classDesc: 统一异常处理
 */
@Slf4j
public class GatewayJsonExceptionHandler implements ErrorWebExceptionHandler {

    private AccessLogService accessLogService;

    public GatewayJsonExceptionHandler(AccessLogService accessLogService) {
        this.accessLogService = accessLogService;
    }

    /**
     * MessageReader
     */
    private List<HttpMessageReader<?>> messageReaders = Collections.emptyList();

    /**
     * MessageWriter
     */
    private List<HttpMessageWriter<?>> messageWriters = Collections.emptyList();

    /**
     * ViewResolvers
     */
    private List<ViewResolver> viewResolvers = Collections.emptyList();

    /**
     * 存储处理异常后的信息
     */
    private ThreadLocal<R> exceptionHandlerResult = new ThreadLocal<>();

    /**
     * 参考AbstractErrorWebExceptionHandler
     *
     * @param messageReaders
     */
    public void setMessageReaders(List<HttpMessageReader<?>> messageReaders) {
        Assert.notNull(messageReaders, "'messageReaders' must not be null");
        this.messageReaders = messageReaders;
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     *
     * @param viewResolvers
     */
    public void setViewResolvers(List<ViewResolver> viewResolvers) {
        this.viewResolvers = viewResolvers;
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     *
     * @param messageWriters
     */
    public void setMessageWriters(List<HttpMessageWriter<?>> messageWriters) {
        Assert.notNull(messageWriters, "'messageWriters' must not be null");
        this.messageWriters = messageWriters;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        /**
         * 按照异常类型进行处理
         */
        HttpStatus httpStatus;
        String message = null;
        R resultBody = null;
        ServerHttpRequest request = exchange.getRequest();
        if ("/favicon.ico".equals(request.getURI().getPath())) {
            return Mono.empty();
        }
        if (ex instanceof NotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
            message = "Service Not Found";
        } else if (ex instanceof ResponseStatusException) {
            ResponseStatusException responseStatusException = (ResponseStatusException) ex;
            httpStatus = responseStatusException.getStatus();
            message = responseStatusException.getMessage();
        } else if (ex instanceof SignatureException) {
            SignatureException signatureException = (SignatureException) ex;
            message = signatureException.getMessage();
            httpStatus = HttpStatus.FORBIDDEN;
        } else if (ex instanceof ApiException) {
            ApiException apiException = (ApiException) ex;
            message = apiException.getMessage();
            httpStatus = HttpStatus.UNAUTHORIZED;
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
            message = "Internal Server Error";
        }

        resultBody = R.error(httpStatus.value(), message, StringUtils.getExceptionToString(ex))
                .put("httpStatus", httpStatus);


        /**
         * 参考AbstractErrorWebExceptionHandler
         */
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }
        exceptionHandlerResult.set(resultBody);
        ServerRequest newRequest = ServerRequest.create(exchange, this.messageReaders);
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse).route(newRequest)
                .switchIfEmpty(Mono.error(ex))
                .flatMap((handler) -> handler.handle(newRequest))
                .flatMap((response) -> {
                    return write(exchange, response, ex);
                });
    }

    /**
     * 参考DefaultErrorWebExceptionHandler
     *
     * @param request
     * @return
     */
    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        R result = exceptionHandlerResult.get();
        HttpStatus httpStatus = (HttpStatus) result.get("httpStatus");
        result.remove("httpStatus");
        return ServerResponse.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(result));
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     *
     * @param exchange
     * @param response
     * @return
     */
    private Mono<? extends Void> write(ServerWebExchange exchange,
                                       ServerResponse response, Throwable ex) {
        exchange.getResponse().getHeaders()
                .setContentType(response.headers().getContentType());
        // 保存日志
        R result = exceptionHandlerResult.get();
        accessLogService.sendLog(exchange, (Exception) ex,result.toString());
        return response.writeTo(exchange, new ResponseContext());
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     */
    private class ResponseContext implements ServerResponse.Context {

        @Override
        public List<HttpMessageWriter<?>> messageWriters() {
            return GatewayJsonExceptionHandler.this.messageWriters;
        }

        @Override
        public List<ViewResolver> viewResolvers() {
            return GatewayJsonExceptionHandler.this.viewResolvers;
        }

    }
}
