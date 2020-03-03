package com.github.cloud.core.aop;

import com.alibaba.fastjson.JSONObject;
import com.github.cloud.core.annotation.PageQuery;
import com.github.cloud.core.base.PageDTO;
import com.github.cloud.core.base.R;
import com.github.cloud.core.util.WebUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 注解分页
 */
@Aspect
@Component
public class PageQueryAspect {

    private static final int PAGE_NUM = 1;

    private static final int PAGE_SIZE = 10;

    private static Logger LOGGER = LoggerFactory.getLogger(PageQueryAspect.class);

    @Around("@annotation(com.github.cloud.core.annotation.PageQuery)")
    public <T> R PageQuery(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Method method = signature.getMethod();
        PageQuery pageQuery = method.getAnnotation(PageQuery.class);

        R result = null;
        long startTime = System.currentTimeMillis();

        if (pageQuery != null) {
            String pageNumParameterName = pageQuery.pageNumParameterName();
            String pageSizeParameterName = pageQuery.pageSizeParameterName();
            //获取request，从中获取分页参数
            ServletRequestAttributes currentRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                    .currentRequestAttributes();
            HttpServletRequest request = currentRequestAttributes.getRequest();

            String pageNum = request.getParameter(pageNumParameterName);
            String pageSize = request.getParameter(pageSizeParameterName);

            String contentType = request.getContentType();
            if (contentType != null && contentType.contains("application/json")) {
                JSONObject reqJson = WebUtils.getRequestJsonObject(request);
                if(reqJson != null) {
                    pageNum = reqJson.getString(pageNumParameterName);
                    pageSize = reqJson.getString(pageSizeParameterName);
                }

            }
            if (StringUtils.isNotBlank(pageNum) && StringUtils.isNotBlank(pageSize)) {
                PageHelper.startPage(Integer.valueOf(pageNum), Integer.valueOf(pageSize));
            } else {
                PageHelper.startPage(PAGE_NUM, PAGE_SIZE);
            }
            try {
                result = (R) joinPoint.proceed();
            } finally {//保证线程变量被清除
                if (PageHelper.getLocalPage() != null)
                    PageHelper.clearPage();
            }

        }

        PageInfo<T> pageInfo = new PageInfo<>((List<T>) result.get("data"));
        result.put("data", pageInfo.getList());
        result.put("pageInfo", new PageDTO(pageInfo.getTotal(),pageInfo.getPageSize(),pageInfo.getPageNum()));

        long endTime = System.currentTimeMillis();
        String methodName = method.getName();
        LOGGER.info("{}耗时：{}毫秒", methodName, (endTime - startTime));

        return result;

    }

}
