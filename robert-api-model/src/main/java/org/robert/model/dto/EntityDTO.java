package org.robert.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EntityDTO {

    private Long id;

    private Long createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
