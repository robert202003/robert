package org.robert.core.face;

import org.robert.core.base.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public interface EntityInterface<T extends BaseEntity> extends Serializable {

    default void onSaveOrUpdate(T model){
            model.setCreateTime(new Date());
            model.setUpdateTime(new Date());
    }
}
