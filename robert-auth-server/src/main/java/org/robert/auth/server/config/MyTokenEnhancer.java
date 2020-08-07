package org.robert.auth.server.config;

import org.robert.auth.server.dto.AuthUserDTO;
import org.robert.auth.server.service.AccountUserDetailsService;
import org.robert.core.constant.SecurityConstants;
import org.robert.core.util.SpringContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 自定义token生成携带的信息
 */
public class MyTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        final Map<String, Object> additionalInfo = new HashMap<>(16);

        Authentication userAuthentication = authentication.getUserAuthentication();
        Object details = userAuthentication.getDetails();
        if (details instanceof LinkedHashMap) {
            Map<String, String> detailsMap = (LinkedHashMap) details;
            additionalInfo.put(SecurityConstants.CLIENT_SECRET, detailsMap.get(SecurityConstants.CLIENT_SECRET));
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof AuthUserDTO) {
            AuthUserDTO user = (AuthUserDTO) principal;
            Long userId = user.getUserId();
            additionalInfo.put("loginDateTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            additionalInfo.put("userId", userId);
            additionalInfo.put("userName", user.getUsername());
            additionalInfo.put("userType", user.getUserType());
            additionalInfo.put("orgId", user.getOrgId());
            additionalInfo.put("appId", user.getAppId());

        } else {
            //刷新token的处理情况
            OAuth2Request oAuth2Request = authentication.getOAuth2Request();
            TokenRequest refreshTokenRequest = oAuth2Request.getRefreshTokenRequest();
            Map<String, String> requestParameters = refreshTokenRequest.getRequestParameters();
            String client_secret = requestParameters.get(SecurityConstants.CLIENT_SECRET);
            String clientId = oAuth2Request.getClientId();
            String userName = (String)principal;
            String name  = userName.concat("|").concat(clientId);
            AuthUserDTO userDetails = (AuthUserDTO) SpringContextHolder.getBean(AccountUserDetailsService.class).loadUserByUsername(name);
            additionalInfo.put("userId", userDetails.getUserId());
            additionalInfo.put("userName", userName);
            additionalInfo.put("userType", userDetails.getUserType());
            additionalInfo.put("orgId", userDetails.getOrgId());
            additionalInfo.put("appId", userDetails.getAppId());
            additionalInfo.put(SecurityConstants.CLIENT_SECRET, client_secret);
        }

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }

}
