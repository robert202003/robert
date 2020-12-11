package org.robert.core.exception;

import org.robert.core.base.R;
import org.robert.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 统一异常处理器
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {


    /**
     * 统一异常处理
     * AuthenticationException
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({AuthenticationException.class})
    public static R authenticationException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        R r = resolveException(ex, request.getRequestURI());
        response.setStatus(r.getHttpStatusCode());
        return r;
    }

    /**
     * OAuth2Exception
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({OAuth2Exception.class, InvalidTokenException.class})
    public static R oauth2Exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        R r = resolveException(ex, request.getRequestURI());
        response.setStatus(r.getHttpStatusCode());
        return r;
    }

    /**
     * ApiException
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({ApiException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static R apiException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        R r = resolveException(ex, request.getRequestURI());
        response.setStatus(r.getHttpStatusCode());
        return r;
    }

    /**
     * BeansException
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({BeansException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public static R beansException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        R r = resolveException(ex, request.getRequestURI());
        response.setStatus(r.getHttpStatusCode());
        return r;
    }




    /**
     * 其他异常
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({Exception.class})
    public static R exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        R r = resolveException(ex, request.getRequestURI());
        response.setStatus(r.getHttpStatusCode());
        return r;
    }

    /**
     * 静态解析异，可以直接调用
     *
     * @param ex
     * @return
     */
    public static R resolveException(Exception ex, String path) {
        int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String message = ex.getMessage();
        if (ex instanceof UsernameNotFoundException) {
            statusCode = HttpStatus.UNAUTHORIZED.value();
        } else if (ex instanceof ApiException) {
            ApiException apiEx = (ApiException) ex;
            int code = apiEx.getStatusCode();
            if (code != 0) {
                statusCode = code;
            }
        }
        return buildBody(ex, statusCode, message);
    }


    /**
     * 构建返回结果对象
     *
     * @param exception
     * @return
     */
    private static R buildBody(Exception exception, int code, String message) {
        R r = R.error(code, message, StringUtils.getExceptionToString(exception));
        log.error("==> error:{} exception: {}", r, exception);
        return r;
    }


    /**
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BindException.class)
    public R handlerNotValidException(BindException ex) {
        log.debug("begin resolve argument exception");
        BindingResult result = ex.getBindingResult();
        String message;

        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            message = fieldErrors.get(0).getDefaultMessage();
        } else {
            message = "";
        }

        return buildBody(ex, 403, message);
    }


    /**
     * 参数不合法
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handler(final MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return buildBody(ex, 403, message);
    }

    /**
     * 参数不合法
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ConstraintViolationException.class)
    public R handler(final ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        return buildBody(ex, 403, message);
    }

}
