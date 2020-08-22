package org.robert.auth.server.constant;

/**
 * Security 权限常量
 *
 */
public interface SecurityConstants {

    /**
     * 基础角色
     */
    String BASE_ROLE = "ROLE_USER";

    /**
     * 授权码模式
     */
    String AUTHORIZATION_CODE = "authorization_code";

    /**
     * 密码模式
     */
    String PASSWORD = "password";

    /**
     * 刷新token
     */
    String REFRESH_TOKEN = "refresh_token";

    /**
     * auth token
     */
    String OAUTH_TOKEN_URL = "/auth/token";

    /**
     * 缓存client的redis key，这里是hash结构存储
     */
    String CACHE_CLIENT_KEY = "oauth_client_details";
    /**
     * OAUTH模式登录处理地址
     */
    String OAUTH_LOGIN_PRO_URL = "/user/login";
    /**
     * PASSWORD模式登录处理地址
     */
    String PASSWORD_LOGIN_PRO_URL = "/auth/user/token";
    /**
     * 获取授权码地址
     */
    String AUTH_CODE_URL = "/auth/authorize";

    /**
     * 默认token过期时间(1小时)
     */
    Integer ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60;
    /**
     * redis中授权token对应的key
     */
    String REDIS_TOKEN_AUTH = "auth:";
    /**
     * redis中应用对应的token集合的key
     */
    String REDIS_CLIENT_ID_TO_ACCESS = "client_id_to_access:";
    /**
     * redis中用户名对应的token集合的key
     */
    String REDIS_UNAME_TO_ACCESS = "uname_to_access:";
    /**
     * rsa公钥
     */
    String RSA_PUBLIC_KEY = "public.cert";

    String AUTHORIZATION = "Authorization";

    /**
     * 客户端密钥
     */
    String CLIENT_SECRET = "client_secret";

    /**
     * 客户端app_id
     */
    String APP_ID = "app_id";

    /**
     * Bearer
     */
    String BEARER_TYPE = "Bearer";

    /**
     * client_id
     */
    String CLIENT_ID = "client_id";

    /**
     * 用户ID
     */
    String USER_ID = "userId";

    /**
     * 机构ID
     */
    String ORG_ID = "orgId";

    /**
     * 用户类型
     */
    String USER_TYPE = "userType";

    /**
     * 用户名（账号）
     */
    String USER_NAME = "userName";

}
