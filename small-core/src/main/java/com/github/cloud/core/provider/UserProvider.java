package com.github.cloud.core.provider;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

public class UserProvider extends MapperTemplate {
    public UserProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String disable(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append(" set ").append("is_locked").append(" = ").append("1");
        sql.append(SqlHelper.wherePKColumns(entityClass, true));
        return sql.toString();
    }

    public String enable(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append(" set ").append("is_locked").append(" = ").append("0");
        sql.append(SqlHelper.wherePKColumns(entityClass, true));
        return sql.toString();
    }

    public String updateLastLoginDate(MappedStatement ms){
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append(" set ").append("last_login_date").append(" = ").append(" now() ");
        sql.append(SqlHelper.wherePKColumns(entityClass, true));
        return sql.toString();
    }

    public String delFlag(MappedStatement ms){
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
        sql.append(" set ").append("del_flag").append(" = ").append("1");
        sql.append(SqlHelper.wherePKColumns(entityClass, true));
        return sql.toString();
    }

}
