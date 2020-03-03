package com.github.cloud.core.resolver;

import com.alibaba.fastjson.JSONObject;
import com.github.cloud.core.annotation.CurrentUser;
import com.github.cloud.core.constant.SecurityConstants;
import com.github.cloud.core.model.AuthUser;
import com.github.cloud.core.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * 含有 @CurrentUser AuthUser user 注解的方法参数注入当前登录用户的信息
 */
@Slf4j
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        String authorization = request.getHeader(SecurityConstants.AUTHORIZATION);
        AuthUser user = new AuthUser();
        if (authorization != null) {
            try {
                String token = authorization.substring("Bearer".length()).trim();
                JSONObject jsonObject = JwtUtils.decodeAndVerify(token);
                CurrentUser loginUser = methodParameter.getParameterAnnotation(CurrentUser.class);
                Long userId = jsonObject.getLong("userId");
                Long orgId = jsonObject.getLong("orgId");
                String client_id = jsonObject.getString("client_id");
                String user_name = jsonObject.getString("user_name");
                String userType = jsonObject.getString("userType");
                user.setApp_id(client_id);
                user.setUserId(userId);
                user.setUserName(user_name);
                user.setToken(token);
                user.setUserType(userType);
                user.setOrgId(orgId);
            } catch (Exception e) {
                log.error("CurrentUserMethodArgumentResolver解析用户信息发生了错误...");
            }

        }
        return user;
    }
}
