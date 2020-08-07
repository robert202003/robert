package org.robert.user.api.mapper;

import org.robert.core.base.MyMapper;
import org.robert.core.mapper.UserMapper;
import org.robert.user.api.dto.SysUserDTO;
import org.robert.user.api.entity.SysUser;

import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @since 2019-11-18
 */
public interface SysUserMapper extends MyMapper<SysUser>, UserMapper<SysUser> {

    /**
     * 根据条件分页查询用户对象
     *
     * @param sysUserDTO 用户信息
     * @return 用户信息集合信息
     */
     List<SysUserDTO> selectUserList(SysUserDTO sysUserDTO);

    /**
     * 根据id查询用户对象
     * @param userId 用户信息
     * @return 用户信息
     */
    SysUserDTO selectUserById(Long userId);

}
