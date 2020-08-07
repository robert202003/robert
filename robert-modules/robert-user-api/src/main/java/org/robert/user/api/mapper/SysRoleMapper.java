package org.robert.user.api.mapper;

import org.robert.core.base.MyMapper;
import org.robert.user.api.dto.SysRoleDTO;
import org.robert.user.api.entity.SysRole;

import java.util.List;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @since 2019-11-18
 */
public interface SysRoleMapper extends MyMapper<SysRole> {
    List<SysRoleDTO> selectRolesByUserId(SysRoleDTO sysUserDTO);
}
