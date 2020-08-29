package org.robert.user.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.robert.auth.server.model.dto.OauthTokenDTO;
import org.robert.auth.server.model.dto.RefreshTokenDTO;
import org.robert.core.base.R;
import org.robert.user.api.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user/")
public class LoginController {

    @Autowired
    private SysUserService userService;

    /**
     * 登录接口，向认证授权中心获取token
     *
     * @param client
     * @return
     */
    @PostMapping("login")
    public R login(@Validated @RequestBody OauthTokenDTO client) {
        try {
            return R.ok(userService.login(client));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return R.error("账号或密码错误");
    }


    /**
     * 刷新token接口，向认证授权中心获取refresh_token
     *
     * @param client
     * @return
     */
    @PostMapping("refreshToken")
    public R refresh_token(@Validated @RequestBody RefreshTokenDTO client) {
        try {
            return R.ok(userService.refreshToken(client));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return R.error("刷新token失败");
    }

    /**
     * 退出登录
     *
     * @param token
     * @return
     */
    @PostMapping("logout")
    public R logout(@RequestParam String token) {
        userService.logout(token);
        return R.ok();
    }

}
