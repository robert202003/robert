package com.github.cloud.user.mapper;

import com.github.cloud.core.base.MyMapper;
import com.github.cloud.user.dto.SysRoleDTO;
import com.github.cloud.user.model.SysRole;

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
