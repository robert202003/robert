package org.robert.auth.server.enums;

public enum UserTypeEnum {

    //--------------------(用户类型:0平台人员,1机构人员,2设备终端用户')------------------
    PLATFORM("0", "平台人员"),
    ORG("1", "机构人员"),
    CLIENT("2", "设备终端用户");

    private final String key;
    private final String info;

    private UserTypeEnum(String key, String info){
        this.key = key;
        this.info = info;
    }

    public String getKey() {
        return key;
    }

    public String getInfo() {
        return info;
    }

    public static UserTypeEnum getUserTypeByKey(Class<UserTypeEnum> clazz, String key) {
        for (UserTypeEnum _enum : clazz.getEnumConstants())
            if (_enum.getKey().equals(key))
                return _enum;
        return null;
    }
    public static UserTypeEnum getUserTypeByInfo(Class<UserTypeEnum> clazz, String info) {
        for (UserTypeEnum _enum : clazz.getEnumConstants())
            if (_enum.getInfo().equals(info))
                return _enum;
        return null;
    }

}
