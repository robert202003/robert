package org.robert.auth.server.mapper;

import org.robert.model.OauthClientDetailsDTO;
import org.robert.auth.server.model.SysUser;
import org.apache.ibatis.annotations.Param;
import org.robert.core.base.MyMapper;

import java.util.List;

public interface AuthMapper extends MyMapper<SysUser> {

    SysUser getUserByUserName(String userName);

    SysUser getUserByUserNameAndAppId(@Param("userName") String userName, @Param("appId") String appId);

    OauthClientDetailsDTO getOauthClientDetailsByClientId(String clientId);

    String getResourceIdsByClientId(String clientId);

    List<Long> getRolesByUserId(Long userId);

    List<String> listUserPermsByUid(Long uid);
}
