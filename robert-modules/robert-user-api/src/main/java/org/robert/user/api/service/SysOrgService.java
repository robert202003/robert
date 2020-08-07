package org.robert.user.api.service;

import org.robert.user.api.dto.SysOrgDTO;
import org.robert.user.api.model.SysOrg;

import java.util.List;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @since 2019-11-18
 */
public interface SysOrgService {

    /**
     * 机构列表
     * @param orgDTO
     * @return
     */
    List<SysOrg> list(SysOrgDTO orgDTO);
}
