package org.robert.log;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

public class RequestLog<T> {

    private String uri;
    private String applicationName;
    private String sysCode;
    private String method;
    private Map<String, String> headers;
    private LocalDateTime reqTime;
    private LocalDateTime respTime;
    private String queryString;
    private String traceId;
    private String status;
    private T requestBody;
    private T responseBody;


    public static RequestLog buildRequestLog(HttpServletRequest request, String sysCode, String sysName) {
        RequestLog requestLog = new RequestLog();
        requestLog.setUri(request.getRequestURI());
        requestLog.setSysCode(sysCode);
        requestLog.setApplicationName(sysName);
        requestLog.setMethod(request.getMethod());
        requestLog.setTraceId(String.valueOf(request.getAttribute("traceId")));
        requestLog.setReqTime(LocalDateTime.now());
        return requestLog;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public LocalDateTime getReqTime() {
        return reqTime;
    }

    public void setReqTime(LocalDateTime reqTime) {
        this.reqTime = reqTime;
    }

    public LocalDateTime getRespTime() {
        return respTime;
    }

    public void setRespTime(LocalDateTime respTime) {
        this.respTime = respTime;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(T requestBody) {
        this.requestBody = requestBody;
    }

    public T getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(T responseBody) {
        this.responseBody = responseBody;
    }


}
