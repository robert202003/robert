package com.github.cloud.core.mapper;

import com.github.cloud.core.provider.UserProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;

@RegisterMapper
public interface UserMapper<T> {
    @UpdateProvider(type = UserProvider.class, method = "dynamicSQL")
    int disable(T value);

    @UpdateProvider(type = UserProvider.class, method = "dynamicSQL")
    int enable(T value);

    @UpdateProvider(type = UserProvider.class, method = "dynamicSQL")
    int updateLastLoginDate(T value);

    @UpdateProvider(type = UserProvider.class, method = "dynamicSQL")
    int delFlag(T value);
}
