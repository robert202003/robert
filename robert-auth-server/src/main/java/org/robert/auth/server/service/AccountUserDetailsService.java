package org.robert.auth.server.service;

import org.robert.auth.server.model.AuthUserDTO;
import org.robert.auth.server.mapper.AuthMapper;
import org.robert.auth.server.model.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.robert.core.enums.UserTypeEnum;
import org.robert.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 根据用户名去查用户密码，用户所拥有的角色等信息，
 * 给security去验证用户登录时的用户名和密码是否正确。
 */
@Slf4j
@Service
public class AccountUserDetailsService implements UserDetailsService {

    @Autowired
    private AuthMapper authMapper;

    /**
     * 查询用户 支持多租户
     * <p>
     * name 等于userName|appId
     *
     * @param name
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        SysUser user;
        if (!name.contains("|")) {
            user = authMapper.getUserByUserName(name);
        } else {
            String[] account = StringUtils.split(name, "|");
            String userName = account[0];
            String clientId = account[1];
            user = authMapper.getUserByUserNameAndAppId(userName, clientId);
        }
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        // 这里设置权限和角色ROLE_开头
        Set<GrantedAuthority> authorities = new HashSet<>();
        String password = user.getPassword();
        Long userId = user.getUserId();
        String appId = user.getAppId();
        String userType = user.getUserType();
        String userName = user.getUserName();
        Long orgId = user.getOrgId();
        List<Long> roles;
        List<String> perms;

        UserTypeEnum userTypeEnum = UserTypeEnum.getUserTypeByKey(UserTypeEnum.class, userType);
        if (userTypeEnum != null) {
            switch (userTypeEnum) {
                case CLIENT:
                    roles = authMapper.getRolesByUserId(userId);
                    roles.forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                    });
                    break;
                case ORG:
                case PLATFORM:
                    perms = authMapper.listUserPermsByUid(userId);
                    perms.forEach(perm -> {
                        if (StringUtils.isNotBlank(perm)) {
                            authorities.add(new SimpleGrantedAuthority(perm));
                        }
                    });
                    break;
                default:
                    break;
            }
        }

        AuthUserDTO authUserDTO = new AuthUserDTO(userId, userName, userType, orgId, appId, password, authorities);
        log.info("当前登录的用户是：{},app_id 是：{}", userName, appId);
        return authUserDTO;
    }

}
