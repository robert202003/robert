package org.robert.user.api.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.robert.core.constant.RedisConstant;
import org.robert.core.context.LangContextHolder;
import org.robert.core.context.RobertContextHolder;
import org.robert.core.exception.ApiException;
import org.robert.core.util.*;
import org.robert.model.dto.OauthTokenDTO;
import org.robert.model.dto.RefreshTokenDTO;
import org.robert.model.dto.SysRoleDTO;
import org.robert.model.dto.SysUserDTO;
import org.robert.model.dto.SysUserDTO;
import org.robert.user.api.entity.SysUser;
import org.robert.user.api.entity.SysUserRole;
import org.robert.user.api.feign.OauthFeignClient;
import org.robert.user.api.mapper.SysUserMapper;
import org.robert.user.api.mapper.SysUserRoleMapper;
import org.robert.user.api.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @since 2019-11-18
 */
@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private OauthFeignClient oauthFeign;

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 登录获取token
     *
     * @param client
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, String> login(OauthTokenDTO client) throws Exception {
        String client_id = client.getClient_id();
        String username = client.getUsername();
        Map<String, Object> requestData = BeanUtils.beanToMap(client);
        requestData.put("username", username.concat("|").concat(client_id));
        Map<String, String> oauthToken = oauthFeign.getOauthToken(requestData);
        oauthToken.remove("client_secret");
        String access_token = oauthToken.get("access_token");
        String userId = oauthToken.get("userId");
        String expires_in = oauthToken.get("expires_in");
        redisTemplate.opsForValue().set(RedisConstant.USER_TOKEN + userId,
                access_token, Long.valueOf(expires_in), TimeUnit.SECONDS);
        //更新登录时间
        Long id = Long.valueOf(oauthToken.get("userId"));
        return oauthToken;
    }


    /**
     * 刷新token
     * @param client
     * @return
     */
    @Override
    public Map<String, String> refreshToken(RefreshTokenDTO client) throws Exception {
        Map<String, String> oauthToken = oauthFeign.getOauthToken(BeanUtils.beanToMap(client));
        oauthToken.remove("client_secret");
        String access_token = oauthToken.get("access_token");
        String expires_in = oauthToken.get("expires_in");
        String userId = oauthToken.get("userId");
        redisTemplate.opsForValue().set(RedisConstant.USER_TOKEN + userId,
                access_token, Long.valueOf(expires_in), TimeUnit.SECONDS);
        return oauthToken;
    }


    /***
     * 条件查询用户列表
     * @param user 用户信息
     * @return
     */
    @Override
    @SentinelResource(value="userList")
    public List<SysUserDTO> selectUserList(SysUserDTO user) {
        String language = LangContextHolder.getLanguage();
        log.info("LanguageContextHolder.getLanguage():" + language);

        String message = MessageUtils.get("robot.user.SysUserDTO.userName.error");
        log.info(message);
        return sysUserMapper.selectUserList(user);
    }

    /**
     * 根据条件查询用户
     *
     * @param userId 用户id
     * @return 用户信息
     */
    @Override
    public SysUserDTO selectUserById(Long userId) {
        return sysUserMapper.selectUserById(userId);
    }


    /***
     * 新增或保存
     * @param user 传入参数
     * @return
     */
    //@TxTransaction
    @Transactional
    @Override
    public void saveUser(SysUserDTO user) {
        Long userId = Long.valueOf(RobertContextHolder.getUserId());
        SysUser sysUser;
        if (user.getUserId() == null) {
            user.setCreateBy(userId);
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateBy(userId);
            user.setUpdateTime(LocalDateTime.now());
            sysUser = this.insert(user);
        } else {
            user.setUpdateBy(userId);
            user.setUpdateTime(LocalDateTime.now());
            sysUser = this.update(user);
        }
        this.updateUserRoles(sysUser.getUserId(), user.getRoles());
    }



    /**
     * 退出登录
     * @param token
     */
    @Override
    public void logout(String token) {
        try {
            JSONObject jsonObject = JwtUtils.decodeAndVerify(token);
            if (jsonObject == null) {
                String userId = jsonObject.getString("userId");
                redisTemplate.delete("user:token:" + userId);
            }
        } catch (Exception e) {
            throw new ApiException("token错误，退出失败");
        }
    }

    private SysUser insert(SysUserDTO user) {
        if (this.existUserName(user.getUserName(),user.getAppId())) {
            throw new ApiException("账号已存在");
        }
        SysUser sysUser = this.createModel(user);
        sysUser.setUserId(snowflakeIdWorker.nextId());
        sysUserMapper.insertSelective(sysUser);
        return sysUser;
    }

    private SysUser update(SysUserDTO user) {
        SysUser source = sysUserMapper.selectByPrimaryKey(user.getUserId());
        if (source != null) {
            String userNameAndAppIdSource=String.format("%s%s", source.getUserName(),source.getAppId());
            String userNameAndAppId=String.format("%s%s", user.getUserName(),user.getAppId());
            if (!userNameAndAppIdSource.equals(userNameAndAppId) && this.existUserName(user.getUserName(),user.getAppId())) {
                throw new ApiException("账号已存在");
            }
        } else {
            throw new ApiException("当前账号不存在，更新失败");
        }
        SysUser sysUser = this.createModel(user);
        sysUserMapper.updateByPrimaryKeySelective(sysUser) ;
        return sysUser;
    }

    private boolean existUserName(String userName,String appId) {
        SysUser sysUser = new SysUser();
        sysUser.setUserName(userName);
        sysUser.setAppId(appId);
        return sysUserMapper.selectCount(sysUser) > 0;
    }

    private SysUser createModel(SysUserDTO user) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(user, sysUser);
        if (StringUtils.isNotBlank(sysUser.getPassword())) {
            sysUser.setPassword(new BCryptPasswordEncoder().encode(sysUser.getPassword()));
        }
        return sysUser;
    }

    @Override
    public void updateUserRoles(Long userId, List<SysRoleDTO> roles) {
        if (userId != null && roles != null && roles.size() > 0) {
            //根据用户ID，删除用户角色
            SysUserRole deleteByUserId = new SysUserRole();
            deleteByUserId.setUserId(userId);
            sysUserRoleMapper.delete(deleteByUserId);
            List<SysUserRole> list = new ArrayList<>();
            for (SysRoleDTO rId : roles) {
                SysUserRole model = new SysUserRole();
                model.setUserId(userId);
                model.setRoleId(rId.getRoleId());
                model.setUserRoleId(snowflakeIdWorker.nextId());
                list.add(model);
            }
            //插入用户角色
            sysUserRoleMapper.insertList(list);
        }
    }


}
