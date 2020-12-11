package org.robert.core.filter;

import com.alibaba.fastjson.JSONObject;
import org.robert.core.constant.SecurityConstants;
import org.robert.core.context.RobertContext;
import org.robert.core.context.RobertContextHolder;
import org.robert.core.util.JwtUtils;
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
                RobertContext context = new RobertContext(userId, appId, orgId, userName, userType);
                RobertContextHolder.setRobotContext(context);
            }

        } finally {

            filterChain.doFilter(request, response);
            RobertContextHolder.clear();
        }


    }
}
