package com.github.cloud.user.service.impl;

import com.github.cloud.user.mapper.SysRoleMapper;
import com.github.cloud.user.model.SysRole;
import com.github.cloud.user.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @since 2019-11-18
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 角色列表
     * @return
     */
    @Override
    public List<SysRole> list() {
        return sysRoleMapper.selectAll();
    }
}
