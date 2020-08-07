package org.robert.user.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class SysMenuDTO {
    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    private String menuName;

    /**
     * 显示顺序
     */
    private Integer menuSort;

    /**
     * 菜单类型（如： 0：目录   1：菜单   2：按钮）
     */
    private String menuType;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 菜单状态 是否可用（0显示 1隐藏）
     */
    private String visible;

    /**
     * 权限标识，授权(多个用逗号分隔，如：user:list,user:create)
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 创建者
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 备注
     */
    private String remark;
}
