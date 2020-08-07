package org.robert.user.api.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.constraints.NotBlank;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class SysUserDTO {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 机构ID
     */
    private Long orgId;
    /**
     * 对应oauth_client_details表的主键client_id
     */
    @NotBlank(message = "{robot.user.SysUserDTO.appId.error}")
    private String appId;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 登录账号
     */
    @NotBlank(message = "{robot.user.SysUserDTO.userName.error}")
    private String userName;

    /**
     * 密码
     */
    @JSONField(serialize = false) //不返回
    @NotBlank(message = "{robot.user.SysUserDTO.password.error}")
    private String password;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * '用户类型:0平台人员,1机构人员,2设备终端用户'
     */
    private String userType;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 用户性别（'1表示男，2表示女，0表示保密'）
     */
    private String gender;

    /**
     * 生日
     */
    private Date birth;

    /**
     * 帐号状态（是否锁定 0正常 1停用）
     */
    private String isLocked;

    /**
     * 最后登陆时间
     */
    private Date lastLoginDate;

    /**
     * '删除状态：0未删除，1已删除'
     */
    private String delFlag;

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

    /** 请求参数 */
    private Map<String, Object> params;

    /** 角色 */
    private List<SysRoleDTO> roles;
}
