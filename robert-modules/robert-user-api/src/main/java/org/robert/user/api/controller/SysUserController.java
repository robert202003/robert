package org.robert.user.api.controller;

import org.robert.core.annotation.PageQuery;
import org.robert.core.context.RobertContextHolder;
import org.robert.core.base.R;
import org.robert.core.util.StringUtils;
import org.robert.model.dto.OauthTokenDTO;
import org.robert.model.dto.RefreshTokenDTO;
import org.robert.model.dto.SysUserDTO;
import org.robert.user.api.feign.GoodsFeignClient;
import org.robert.user.api.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @since 2019-11-18
 */
@Slf4j
@RestController
@RequestMapping("/api/user/")
public class SysUserController {

    @Autowired
    private SysUserService userService;
    @Autowired
    private GoodsFeignClient versionFeign;


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


    @GetMapping("info")
    public R info(HttpServletRequest request) {
        Map<String, String> user = new HashMap<>();

        user.put("name", "罗文喜");
        user.put("avatar", "https://img20.360buyimg.com/popshop/jfs/t2824/34/4130238362/6532/3ab064e2/57aa8583N03118a8b.jpg");
        return R.ok(user);
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

    /***
     * 条件查询 用户列表
     * @param sysUserDTO
     * @return
     */
    @PostMapping("list")
    @PageQuery
    public R selectUserList(@RequestBody(required = false) SysUserDTO sysUserDTO, HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        String userId = RobertContextHolder.getUserId();
        System.out.println(userId);

        // R tree = versionFeign.tree();
        return R.ok(userService.selectUserList(sysUserDTO));
    }

    /***
     * 查询用户
     * @param userId
     * @return
     */
    @GetMapping("select/{userId}")
    public R selectUserById(@PathVariable("userId") Long userId) {
        return R.ok(userService.selectUserById(userId));
    }

    @PostMapping("save")
    public R saveUser(@RequestBody(required = false) @Valid SysUserDTO sysUserDTO) {
        userService.saveUser(sysUserDTO);
        return R.ok();
    }

    @DeleteMapping("delete/{userId}")
    public R deleteUser(@PathVariable("userId") Long userId) {
        return R.ok(userService.deleteUser(userId));
    }

    @PostMapping({"locked/{userId}/{flag}", "locked/{userId}"})
    public R setLocked(@PathVariable("userId") Long userId, @PathVariable(required = false) String flag) {
        boolean isLocked = false;
        if (StringUtils.isNotBlank(flag)) {
            isLocked = flag.equals("1");
        } else {
            SysUserDTO sysUser = userService.selectUserById(userId);
            if (sysUser != null) {
                isLocked = sysUser.getIsLocked().equals("1") ? false : true;
            }
        }
        return R.ok(userService.setLocked(userId, isLocked));
    }

}
