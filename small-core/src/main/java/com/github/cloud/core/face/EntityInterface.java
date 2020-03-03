package com.github.cloud.core.face;

import com.github.cloud.core.base.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public interface EntityInterface<T extends BaseEntity> extends Serializable {

    default void onSaveOrUpdate(T model){
            model.setCreateTime(new Date());
            model.setUpdateTime(new Date());
    }
}
