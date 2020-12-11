package org.robert.core.context;

/**
 * 用户信息上下文信息
 */
public class RobertContextHolder {

    private static final ThreadLocal<RobertContext> robotContext = new ThreadLocal<>();

    public static void setRobotContext(RobertContext context) {
        robotContext.set(context);
    }

    public static RobertContext getRobotContext() {

        return robotContext.get();
    }

    public static void clear() {
        robotContext.remove();
    }

    /**
     * 获取用户ID
     * @return
     */
    public static String getUserId() {

        return getRobotContext().getUserId();
    }

    /**
     * 获取用户ID
     * @return
     */
    public static Long getUserIdAsLong() {

        return Long.valueOf(getUserId());
    }


    /**
     * 获取机构ID
     * @return
     */
    public static String getOrgId() {
        return getRobotContext().getOrgId();
    }

    /**
     * 获取client_id
     * @return
     */
    public static String getClientId() {
        return getRobotContext().getAppId();
    }

    /**
     * 获取用户账号信息
     * @return
     */
    public static String getUserName() {
        return getRobotContext().getUserName();
    }

    /**
     * 获取用户类型
     * @return
     */
    public static String getUserType() {
        return getRobotContext().getUserType();
    }

}
