package com.github.cloud.user.service.impl;

import com.github.cloud.core.util.BeanUtils;
import com.github.cloud.user.dto.SysOrgDTO;
import com.github.cloud.user.mapper.SysOrgMapper;
import com.github.cloud.user.model.SysOrg;
import com.github.cloud.user.service.SysOrgService;
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
