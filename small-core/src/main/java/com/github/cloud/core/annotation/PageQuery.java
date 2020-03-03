package com.github.cloud.core.annotation;

import java.lang.annotation.*;

/**
 * 用于方法上，可自动分页
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PageQuery {

    /** 页号的参数名 */
    String pageNumParameterName() default "pageNum";

    /** 每页行数的参数名 */
    String pageSizeParameterName() default "pageSize";
}
