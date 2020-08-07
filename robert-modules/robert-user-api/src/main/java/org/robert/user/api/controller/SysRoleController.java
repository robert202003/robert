package com.github.cloud.user.controller;

import com.github.cloud.core.annotation.PageQuery;
import com.github.cloud.core.base.R;
import com.github.cloud.user.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色信息表 前端控制器
 * </p>
 *
 * @since 2019-11-18
 */
@RestController
@RequestMapping("/api/role/")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 角色列表，带分页
     * @return
     */
    @PageQuery
    @PostMapping("list")
    public R list() {

        return R.ok(sysRoleService.list());
    }

    @PostMapping("/update")
    public R updateRole() {
        return R.ok();
    }


    @PostMapping("/save")
    public R save() {
        return R.ok();
    }

}
