package org.robert.user.api.service.impl;

import org.robert.core.util.BeanUtils;
import org.robert.user.api.dto.SysOrgDTO;
import org.robert.user.api.mapper.SysOrgMapper;
import org.robert.user.api.entity.SysOrg;
import org.robert.user.api.service.SysOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @since 2019-11-18
 */
@Service
public class SysOrgServiceImpl implements SysOrgService {

    @Autowired
    private SysOrgMapper sysOrgMapper;

    /**
     * 机构列表
     * @param orgDTO
     * @return
     */
    @Override
    public List<SysOrg> list(SysOrgDTO orgDTO) {
        SysOrg sysOrg = new SysOrg();
        BeanUtils.copyProperties(orgDTO,sysOrg);
        return sysOrgMapper.select(sysOrg);
    }
}
