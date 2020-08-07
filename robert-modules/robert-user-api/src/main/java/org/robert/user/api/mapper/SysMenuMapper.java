package com.github.cloud.user.mapper;

import com.github.cloud.core.base.MyMapper;
import com.github.cloud.user.model.SysMenu;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @since 2019-11-18
 */
public interface SysMenuMapper extends MyMapper<SysMenu> {

    List<SysMenu> listMenuByUserId(Long uid);

    List<String> listUserPermsByUid(Long uid);

}
