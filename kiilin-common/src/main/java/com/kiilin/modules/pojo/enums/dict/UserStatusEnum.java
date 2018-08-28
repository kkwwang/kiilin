package com.kiilin.modules.pojo.enums.dict;

import com.kiilin.common.config.IDictEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 用户状态枚举
 *
 * @author wangkai
 */

public enum UserStatusEnum implements IDictEnum {

    /**
     * 正常
     */
    RIGHT(0, "正常"),
    /**
     * 停用
     */
    STOP(1, "停用"),
    /**
     * 冻结
     */
    LOCK(1, "冻结"),


    /**
     * ---------------------------------------------------- 华丽的分割线 ----------------------------------------------------
     */
    ;


    /**
     * 值
     */
    private Integer value;

    /**
     * 描述
     */
    private String desc;

    UserStatusEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public UserStatusEnum setValue(Integer value) {
        this.value = value;
        return this;
    }

    @Override
    @JsonValue
    public String getDesc() {
        return desc;
    }

    public UserStatusEnum setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    @Override
    public String toString() {
        return "UserStatusEnum{" +
                "value='" + value + '\'' +
                ", desc='" + desc + '\'' +
                "} " + super.toString();
    }
}
