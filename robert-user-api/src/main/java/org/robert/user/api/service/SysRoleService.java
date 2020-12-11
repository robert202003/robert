package org.robert.user.api.service;


import org.robert.user.api.entity.SysRole;

import java.util.List;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @since 2019-11-18
 */
public interface SysRoleService {

    /**
     * 角色列表
     * @return
     */
    List<SysRole> list();
}
