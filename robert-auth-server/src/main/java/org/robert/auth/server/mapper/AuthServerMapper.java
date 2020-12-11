package org.robert.auth.server.mapper;

import org.robert.auth.server.dto.OauthClientDetailsDTO;
import org.robert.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthServerMapper {

    SysUser getUserByUserName(String userName);

    SysUser getUserByUserNameAndAppId(@Param("userName") String userName, @Param("appId") String appId);

    OauthClientDetailsDTO getOauthClientDetailsByClientId(String clientId);

    String getResourceIdsByClientId(String clientId);

    List<Long> getRolesByUserId(Long userId);

    List<String> listUserPermsByUid(Long uid);
}
