package com.github.cloud.core.filter;

import com.alibaba.fastjson.JSONObject;
import com.github.cloud.core.constant.SecurityConstants;
import com.github.cloud.core.context.LanguageContextHolder;
import com.github.cloud.core.context.RobotContext;
import com.github.cloud.core.context.RobotContextHolder;
import com.github.cloud.core.util.JwtUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ConditionalOnClass(Filter.class)
public class RobotContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String acceptLanguage = request.getHeader("Accept-Language");
        LanguageContextHolder.setLanguage(acceptLanguage);
        try {
            String authorization = request.getHeader(SecurityConstants.AUTHORIZATION);
            if (authorization != null) {
                String token = authorization.substring(SecurityConstants.BEARER_TYPE.length()).trim();
                JSONObject userJson = JwtUtils.decode(token);

                String userId = userJson.getString(SecurityConstants.USER_ID);
                String appId = userJson.getString(SecurityConstants.CLIENT_ID);
                String userType = userJson.getString(SecurityConstants.USER_TYPE);
                String orgId = userJson.getString(SecurityConstants.ORG_ID);
                String userName = userJson.getString(SecurityConstants.USER_NAME);
                RobotContext context = new RobotContext(userId, appId, orgId, userName, userType);
                RobotContextHolder.setRobotContext(context);
            }

        } finally {

            filterChain.doFilter(request, response);
            RobotContextHolder.clear();
            LanguageContextHolder.clearLanguage();
        }


    }
}
