package org.robert.auth.server.dto;

import lombok.Data;

import java.util.Date;

@Data
public class EntityDTO {

    private Long id;

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
}
