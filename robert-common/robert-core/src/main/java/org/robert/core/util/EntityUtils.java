package org.robert.core.util;

import org.robert.core.context.RobertContextHolder;

import java.time.LocalDateTime;


/**
 * 实体类相关工具类
 * 解决问题： 1、快速对实体的常驻字段，如：createBy、createTime、updateBy、updateTime等值快速注入
 */
public class EntityUtils {

    /**
     * 快速将bean的createBy、createTime、updateBy、updateTime附上相关值
     *
     * @param entity 实体bean
     */
    public static <T> void onSaveOrUpdate(T entity) {
        String userId = RobertContextHolder.getUserId();
        // 默认属性
        String[] fields = null;
        // 默认值
        Object[] value = null;
        if (isPKNotNull(entity, "id")) {
            fields = new String[]{"updateBy", "updateTime"};
            value = new Object[]{userId, LocalDateTime.now()};
        } else {
            long id = new SnowflakeIdWorker(1, 1).nextId();
            fields = new String[]{"id", "createBy", "createTime", "updateBy", "updateTime"};
            value = new Object[]{id, userId, LocalDateTime.now(), userId, LocalDateTime.now()};
        }
        // 填充默认属性值
        setDefaultValues(entity, fields, value);
    }

    /**
     * 依据对象的属性数组和值数组对对象的属性进行赋值
     *
     * @param entity 对象
     * @param fields 属性数组
     * @param value  值数组
     */
    private static <T> void setDefaultValues(T entity, String[] fields, Object[] value) {
        for (int i = 0; i < fields.length; i++) {
            String field = fields[i];
            if (ReflectionUtils.hasField(entity, field)) {
                ReflectionUtils.invokeSetter(entity, field, value[i]);
            }
        }
    }

    /**
     * 根据主键属性，判断主键是否值为空
     *
     * @param entity
     * @param field
     * @return 主键为空，则返回false；主键有值，返回true
     */
    public static <T> boolean isPKNotNull(T entity, String field) {
        if (!ReflectionUtils.hasField(entity, field)) {
            return false;
        }
        Object value = ReflectionUtils.getFieldValue(entity, field);
        return value != null && !"".equals(value);
    }
}
