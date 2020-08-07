package org.robert.user.api.controller;

import org.robert.core.annotation.PageQuery;
import org.robert.core.base.R;
import org.robert.user.api.dto.SysOrgDTO;
import org.robert.user.api.service.SysOrgService;
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
