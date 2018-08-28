package com.kiilin.common.config;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import com.kiilin.common.shiro.ShiroUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * 插入/修改 数据库数据时 实体数据填充
 *
 * @author mybatisplus
 */
@Component
public class MetaObjectHandlerConfig extends MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        try {
            // 创建者
            setFieldValByName("createBy", ShiroUtils.getUserId(), metaObject);
        } catch (UnavailableSecurityManagerException e) {
            // 非用户触发
        }

        // 插入方法实体填充
        // id - uuid
        setFieldValByName("id", UUID.randomUUID().toString().replace("-", ""), metaObject);
        // 创建时间
        setFieldValByName("createTime", new Date(), metaObject);
        // 删除标识
        setFieldValByName("delFlag", false, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新方法实体填充
        try {
            // 修改者
            setFieldValByName("updateBy", ShiroUtils.getUserId(), metaObject);
        } catch (UnavailableSecurityManagerException e) {
            // 非用户触发
        }
        // 修改时间
        setFieldValByName("updateTime", new Date(), metaObject);
    }
}
