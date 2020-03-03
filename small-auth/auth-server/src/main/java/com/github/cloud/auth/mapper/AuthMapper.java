package com.github.cloud.auth.mapper;

import com.github.cloud.auth.dto.OauthClientDetailsDTO;
import com.github.cloud.auth.model.SysUser;
import com.github.cloud.core.base.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthMapper extends MyMapper<SysUser> {

    SysUser getUserByUserName(String userName);

    SysUser getUserByUserNameAndAppId(@Param("userName") String userName, @Param("appId") String appId);

    OauthClientDetailsDTO getOauthClientDetailsByClientId(String clientId);

    String getResourceIdsByClientId(String clientId);

    List<Long> getRolesByUserId(Long userId);

    List<String> listUserPermsByUid(Long uid);
}
