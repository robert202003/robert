package org.robert.user.api.service.impl;

import org.robert.core.context.RobotContextHolder;
import org.robert.core.exception.ApiException;
import org.robert.core.model.BuildTree;
import org.robert.core.model.Tree;
import org.robert.core.util.BeanUtils;
import org.robert.core.util.StringUtils;
import org.robert.user.api.dto.SysMenuDTO;
import org.robert.user.api.mapper.SysMenuMapper;
import org.robert.user.api.mapper.SysMenuRoleMapper;
import org.robert.user.api.model.SysMenu;
import org.robert.user.api.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @since 2019-11-18
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper menuMapper;

    @Autowired
    private SysMenuRoleMapper menuRoleMapper;

    @Override
    public List<Tree<SysMenu>> listMenuTree(Long uid) {
        List<Tree<SysMenu>> trees = new ArrayList<Tree<SysMenu>>();
        List<SysMenu> menus = menuMapper.listMenuByUserId(uid);
        for (SysMenu m : menus) {
            Tree<SysMenu> tree = new Tree<SysMenu>();
            tree.setId(m.getMenuId().toString());
            tree.setParentId(m.getParentId().toString());
            tree.setText(m.getMenuName());
            Map<String, Object> attributes = new HashMap<>(16);
            attributes.put("url", m.getUrl());
            attributes.put("icon", m.getIcon());
            tree.setAttributes(attributes);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        List<Tree<SysMenu>> list = BuildTree.buildList(trees, "0");
        return list;
    }

    @Override
    public List<SysMenu> list(Map<String, Object> params) {
        SysMenu sysMenu = new SysMenu();
        List<SysMenu> menus = menuMapper.select(sysMenu);
        return menus;
    }

    @Override
    public Tree<SysMenu> getTree() {
        List<Tree<SysMenu>> trees = new ArrayList<Tree<SysMenu>>();
        List<SysMenu> menuList = menuMapper.select(null);
        for (SysMenu menu : menuList) {
            Tree<SysMenu> tree = new Tree<SysMenu>();
            tree.setId(menu.getMenuId().toString());
            tree.setParentId(menu.getParentId().toString());
            tree.setText(menu.getMenuName());
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("url",menu.getUrl());
            attributes.put("perms",menu.getPerms());
            tree.setAttributes(attributes);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<SysMenu> t = BuildTree.build(trees);
        return t;
    }

    @Override
    public Tree<SysMenu> getTree(Long roleId) {
        // 根据roleId查询权限
        List<SysMenu> menuList = menuMapper.select(null);
        List<Long> menuIds = menuRoleMapper.listMenuIdByRoleId(roleId);
        List<Long> temp = menuIds;
        for (SysMenu menu : menuList) {
            if (temp.contains(menu.getParentId())) {
                menuIds.remove(menu.getParentId());
            }
        }
        List<Tree<SysMenu>> trees = new ArrayList<Tree<SysMenu>>();
        for (SysMenu m : menuList) {
            Tree<SysMenu> tree = new Tree<SysMenu>();
            tree.setId(m.getMenuId().toString());
            tree.setText(m.getMenuName());
            tree.setParentId(m.getParentId().toString());
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("url",m.getUrl());
            attributes.put("perms",m.getPerms());
            tree.setAttributes(attributes);
            Map<String, Object> state = new HashMap<>(16);
            Long menuId = m.getMenuId();
            if (menuIds.contains(menuId)) {
                state.put("selected", true);
            } else {
                state.put("selected", false);
            }
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<SysMenu> t = BuildTree.build(trees);
        return t;
    }

    @Override
    public Set<String> listPerms(Long userId) {
        List<String> perms = menuMapper.listUserPermsByUid(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotBlank(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public boolean saveMenu(SysMenuDTO menu) {
        Long userId = Long.valueOf(RobotContextHolder.getUserId());
        if (menu.getMenuId() == null) {
            menu.setCreateBy(userId);
            menu.setCreateTime(new Date());
            menu.setUpdateBy(userId);
            menu.setUpdateTime(new Date());
            return this.insert(menu);
        } else {
            menu.setUpdateBy(userId);
            menu.setUpdateTime(new Date());
            return this.update(menu);
        }
    }

    @Override
    public boolean deleteMenu(Long menuId) {
        return menuMapper.deleteByPrimaryKey(menuId) > 0;
    }


    private boolean insert(SysMenuDTO menu) {
        if (this.existMenuName(menu.getMenuName())) {
            throw new ApiException("名称已存在");
        }
        SysMenu sysMenu = this.createModel(menu);
        return menuMapper.insertSelective(sysMenu) > 0;
    }

    private boolean update(SysMenuDTO menu) {
        SysMenu source = menuMapper.selectByPrimaryKey(menu.getMenuId());
        if (source != null) {
            if (!source.getMenuName().equals(menu.getMenuName()) && this.existMenuName(menu.getMenuName())) {
                throw new ApiException("名称已存在");
            }
        } else {
            throw new ApiException("当前菜单不存在，更新失败");
        }
        SysMenu sysMenu = this.createModel(menu);
        return menuMapper.updateByPrimaryKeySelective(sysMenu) > 0;
    }

    private boolean existMenuName(String menuName) {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuName(menuName);
        return menuMapper.selectCount(sysMenu) > 0;
    }

    private SysMenu createModel(SysMenuDTO menu) {
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(menu, sysMenu);
        return sysMenu;
    }

}
