package com.github.cloud.user.mapper;

import com.github.cloud.core.base.MyMapper;
import com.github.cloud.user.model.SysMenuRole;

import java.util.List;

/**
 * <p>
 * 角色和菜单关联表 Mapper 接口
 * </p>
 *
 * @since 2019-11-18
 */
public interface SysMenuRoleMapper extends MyMapper<SysMenuRole> {

    List<Long> listMenuIdByRoleId(Long roleId);

}
