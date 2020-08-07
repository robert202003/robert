package org.robert.user.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @since 2019-11-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sys_menu")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    @Id
    @Column(name = "menu_id")
    private Long menuId;

    /**
     * 父菜单ID
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 菜单名称
     */
    @Column(name = "menu_name")
    private String menuName;

    /**
     * 显示顺序
     */
    @Column(name = "menu_sort")
    private Integer menuSort;

    /**
     * 菜单类型（如： 0：目录   1：菜单   2：按钮）
     */
    @Column(name = "menu_type")
    private String menuType;

    /**
     * 请求地址
     */
    @Column(name = "url")
    private String url;

    /**
     * 菜单状态 是否可用（0显示 1隐藏）
     */
    @Column(name = "visible")
    private String visible;

    /**
     * 权限标识，授权(多个用逗号分隔，如：user:list,user:create)
     */
    @Column(name = "perms")
    private String perms;

    /**
     * 菜单图标
     */
    @Column(name = "icon")
    private String icon;

    /**
     * 创建者
     */
    @Column(name = "create_by")
    private Long createBy;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新者
     */
    @Column(name = "update_by")
    private Long updateBy;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;


}
