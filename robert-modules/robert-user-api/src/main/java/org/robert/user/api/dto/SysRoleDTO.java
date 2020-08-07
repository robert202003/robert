package com.github.cloud.user.dto;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class SysRoleDTO {
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色名称 英语
     */
    private String roleNameEn;

    /**
     * 角色权限字符串(标识)
     */
    private String roleSign;

    /**
     * 显示顺序
     */
    private Integer roleSort;

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限）相当于角色类型
     */
    private String dataScope;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @JSONField(serialize = false)
    private String delFlag;

    /**
     * 创建者
     */
    @JSONField(serialize = false)
    private Long createBy;

    /**
     * 创建时间
     */
    @JSONField(serialize = false)
    private Date createTime;

    /**
     * 更新者
     */
    @JSONField(serialize = false)
    private Long updateBy;

    /**
     * 更新时间
     */
    @JSONField(serialize = false)
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

}
