package org.robert.file.api.vo;

import java.util.HashMap;

/**
 * 返回json数据对象封装
 */
public class R extends HashMap<String, Object> {

    private static String MSG = "服务器繁忙，请稍后重试";

    public R() {
        this.put("code", 200);
        this.put("msg", "成功");
        this.put("success", true);
    }

    public static R error() {

        return error(500, MSG);
    }

    public static R error(String msg) {

        return error(500, msg, null);
    }

    public static R error(int statusCode, String errorMsg) {

        return error(statusCode, MSG, errorMsg);
    }

    public static R error(int statusCode, String msg, String errorMsg) {
        return error(statusCode, msg, errorMsg, null);
    }

    public static R error(int statusCode, String msg, String errorMsg, Object data) {
        R r = new R();
        r.put("code", statusCode);
        r.put("msg", msg);
        r.put("errorDetail", errorMsg);
        r.put("success", false);
        if (data != null) {
            r.put("data", data);
        }
        return r;
    }

    public static R ok() {
        return new R();
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Object data) {
        R r = new R();
        if (data != null) {
            r.put("data", data);
        }
        return r;
    }

    public static R ok(String msg, Object data) {
        R r = new R();
        r.put("msg", msg);
        if (data != null) {
            r.put("data", data);
        }
        return r;
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public R code(int code) {
        super.put("code",code);
        return this;
    }

    public R msg(String message) {
        super.put("msg",message);
        return this;
    }

    public R errorDetail(String errorDetail) {
        super.put("errorDetail",errorDetail);
        return this;
    }


    public R httpStatusCode(int httpStatusCode) {
        super.put("httpStatusCode",httpStatusCode);
        return this;
    }

    public int getHttpStatusCode() {
        if (this.get("httpStatusCode") != null) {
            return (int)this.get("httpStatusCode");
        }
        return (int)this.get("code");
    }


}

