package org.robert.user.api.service;

import org.robert.user.api.dto.OauthTokenDTO;

import java.util.Map;

import org.robert.user.api.dto.RefreshTokenDTO;
import org.robert.user.api.dto.SysRoleDTO;
import org.robert.user.api.dto.SysUserDTO;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @since 2019-11-18
 */
public interface SysUserService {

    /**
     * 登录获取token
     *
     * @param client
     * @return
     * @throws Exception
     */
    Map<String, String> login(OauthTokenDTO client) throws Exception;


    /**
     * 刷新token
     * @param client
     * @return
     */
    Map<String, String> refreshToken(RefreshTokenDTO client) throws Exception;

    /**
     * 根据条件分页查询用户对象
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    List<SysUserDTO> selectUserList(SysUserDTO user);

    /**
     * 根据条件查询用户
     * @param userId 用户id
     * @return 用户信息
     */
    SysUserDTO selectUserById(Long userId);

    /**
     *  新增用户
     * @param user 传入参数
     */
    void saveUser(SysUserDTO user);
    /***
     * 根据用户ID 更新 ，登录时间
     * @param userId 用户ID
     */
    boolean updateLastLoginDate(Long userId);

    /***
     * 根据用户ID设置是否锁定
     * @param userId 用户ID
     * @param isLocked  是否锁定
     */
    boolean setLocked(Long userId,boolean isLocked);

    /***
     * 根据用户id删除用户，软删
     * @param userId 用户ID
     */
    boolean deleteUser(Long userId);

    /**
     * 退出登录
     * @param token
     */
    void logout(String token);

    /***
     * @param userId 用户id
     * @param roles 角色s
     * 更新用户角色表sys_user_role
     */
    void updateUserRoles(Long userId, List<SysRoleDTO> roles);


}
