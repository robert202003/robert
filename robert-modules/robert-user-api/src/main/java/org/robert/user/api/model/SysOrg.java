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
 * 机构表
 * </p>
 *
 * @since 2019-11-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sys_org")
public class SysOrg implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 机构id
     */
    @Id
    @Column(name = "org_id")
    private Long orgId;

    /**
     * 机构名称
     */
    @Column(name = "org_name")
    private String orgName;

    /**
     * 机构简称
     */
    @Column(name = "org_short_name")
    private String orgShortName;

    /**
     * 显示顺序
     */
    @Column(name = "org_sort")
    private Integer orgSort;

    /**
     * 机构编码
     */
    @Column(name = "org_code")
    private String orgCode;

    /**
     * 机构地址
     */
    @Column(name = "org_address")
    private String orgAddress;

    /**
     * 机构网址
     */
    @Column(name = "org_url")
    private String orgUrl;

    /**
     * 联系人
     */
    @Column(name = "contact")
    private String contact;

    /**
     * 联系电话
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @Column(name = "del_flag")
    private String delFlag;

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
