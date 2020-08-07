package org.robert.user.api.mapper;

import org.robert.core.base.MyMapper;
import org.robert.user.api.model.SysMenuRole;

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
