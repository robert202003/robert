package com.github.cloud.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.cloud.core.base.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

   /* @GetMapping("/principal")
    public Principal user(@RequestParam(required = false) String clientName) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        return authentication;
    }*/


    @GetMapping("/logout")
    public R logout(@RequestParam String token) {
        boolean b = consumerTokenServices.revokeToken(token);
        return R.ok(b);
    }

    /**
     * 解析jwt token
     *
     * @param token
     * @return
     */
    @GetMapping("/decodeToken")
    public R decodeToken(@RequestParam String token) {
        Jwt decode = JwtHelper.decode(token);
        String claims = decode.getClaims();
        JSONObject jsonObject = JSON.parseObject(claims);
        jsonObject.put("encoded",decode.getEncoded());
        return R.ok(jsonObject);
    }

}
