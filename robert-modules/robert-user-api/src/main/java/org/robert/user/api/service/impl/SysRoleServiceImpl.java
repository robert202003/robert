package org.robert.user.api.service.impl;

import org.robert.user.api.mapper.SysRoleMapper;
import org.robert.user.api.model.SysRole;
import org.robert.user.api.service.SysRoleService;
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
