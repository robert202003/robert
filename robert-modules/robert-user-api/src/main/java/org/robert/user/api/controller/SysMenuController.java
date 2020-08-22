package org.robert.user.api.controller;

import org.robert.core.context.RobertContextHolder;
import org.robert.core.base.R;
import org.robert.core.model.Tree;
import org.robert.user.api.dto.SysMenuDTO;
import org.robert.user.api.entity.SysMenu;
import org.robert.user.api.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @since 2019-11-18
 */
@RestController
@RequestMapping("/api/menu/")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 获取全部菜单树形列表
     * @return
     */
    @PreAuthorize("hasAuthority('admin:menu:menu')")
    @PostMapping("tree")
    public R tree() {
        Tree<SysMenu> tree = sysMenuService.getTree();
        return R.ok(tree);
    }

    /**
     * 根据角色ID获取树形菜单列表
     * @param roleId
     * @return
     */
    @PostMapping("/tree/{roleId}")
    public R tree(@PathVariable("roleId") Long roleId) {
        Tree<SysMenu> tree = sysMenuService.getTree(roleId);
        return R.ok(tree);
    }

    /**
     * 根据用户ID获取树形菜单列表
     * @return
     */
    @PostMapping("getMenus")
    public R getMenus() {
        Long userId = Long.valueOf(RobertContextHolder.getUserId());
        List<Tree<SysMenu>> trees = sysMenuService.listMenuTree(userId);
        return R.ok(trees);
    }

    @PostMapping("save")
    public R saveMenu(@RequestBody(required = false) @Valid SysMenuDTO sysMenuDTO) {
        return R.ok(sysMenuService.saveMenu(sysMenuDTO));
    }

    @DeleteMapping("delete/{menuId}")
    public R deleteMenu(@PathVariable("menuId") Long menuId){
        return R.ok(sysMenuService.deleteMenu(menuId));
    }

}
