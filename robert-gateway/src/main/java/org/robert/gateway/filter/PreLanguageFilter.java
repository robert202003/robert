package org.robert.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Accept-Language前置过滤器
 *
 */
@Component
@Slf4j
public class PreLanguageFilter implements WebFilter {

    /**
     * 支持的语言列表
     */
    private static final String[] LANGUAGE_SUPPORT = new String[]{"en", "es", "fr", "it", "ja", "th", "zh-CN",
                                                                  "zh-TW", "de","ru","sv-SE","cs-CZ", "af", "da",
                                                                   "no","pt","ko","pl","sk","vi","hu"};
    private static final List<String> LANGUAGE_SUPPORT_LIST = Arrays.asList(LANGUAGE_SUPPORT);
    /**
     * Accept-Language为空是默认的语言
     */
    private static final String DEFAULT_LANGUAGE = "en";
    private static final String ACCEPT_LANGUAGE = "Accept-Language";


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        List<String> acceptLanguages = headers.get(ACCEPT_LANGUAGE);
        Consumer<HttpHeaders> httpHeaders = null;
        if (acceptLanguages == null || acceptLanguages.isEmpty()) {
            httpHeaders = httpHeader -> {
                httpHeader.set(ACCEPT_LANGUAGE, DEFAULT_LANGUAGE);
            };
        } else {
            String acceptLanguage = acceptLanguages.get(0);
            if (!LANGUAGE_SUPPORT_LIST.contains(acceptLanguage)) {
                httpHeaders = httpHeader -> {
                    httpHeader.set(ACCEPT_LANGUAGE, DEFAULT_LANGUAGE);
                };
                log.warn("不支持语言={},目前只支持语言={},默认语言={}", acceptLanguage, LANGUAGE_SUPPORT_LIST.toString(),DEFAULT_LANGUAGE);
            }
        }

        if (httpHeaders != null) {
            ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate().headers(httpHeaders).build();
            exchange.mutate().request(serverHttpRequest).build();
        }
        return chain.filter(exchange);
    }

}
