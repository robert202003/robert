package com.github.cloud.user.controller;

import com.github.cloud.core.annotation.PageQuery;
import com.github.cloud.core.base.R;
import com.github.cloud.user.dto.SysOrgDTO;
import com.github.cloud.user.service.SysOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @since 2019-11-18
 */
@RestController
@RequestMapping("/api/org/")
public class SysOrgController {

    @Autowired
    private SysOrgService sysOrgService;

    /**
     * 机构列表，带分页
     * @param orgDTO
     * @return
     */
    @PageQuery
    @PostMapping("list")
    public R list(@RequestBody SysOrgDTO orgDTO) {

        return R.ok(sysOrgService.list(orgDTO));
    }

}
