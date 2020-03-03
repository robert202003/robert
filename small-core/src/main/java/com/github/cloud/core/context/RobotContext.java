package com.github.cloud.core.context;

/**
 * 用户信息
 */
public class RobotContext {

    private String userId;

   // private String language;

    private String appId;

    private String orgId;

    private String userName;

    private String userType;

    public RobotContext(String userId, String appId, String orgId, String userName, String userType) {
        this.userId = userId;
       // this.language = language;
        this.appId = appId;
        this.orgId = orgId;
        this.userName = userName;
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /*public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }*/

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}
