package org.robert.user.api.feign;

import org.robert.core.constant.FeignConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * feign调用robot-oauth服务
 */
@FeignClient(value = FeignConstant.ROBERT_OAUTH)
public interface OauthFeignClient {

    /**
     * 获取token
     *
     * @param username 账号
     * @param password 密码
     * @param client_id 应用ID
     * @param client_secret 客户端秘钥
     * @param grant_type 认证类型
     * @param scope 范围
     * @return
     */
    @PostMapping("/oauth/token")
    Map<String, String> getOauthToken(@RequestParam("username") String username, @RequestParam("password") String password,
                                      @RequestParam("client_id") String client_id, @RequestParam("client_secret") String client_secret,
                                      @RequestParam("grant_type") String grant_type, @RequestParam("scope") String scope);


    /**
     * 获取token
     *
     * @param params
     * @return
     */
    @PostMapping("/oauth/token")
    Map<String, String> getOauthToken(@RequestParam Map<String,Object> params);
}
