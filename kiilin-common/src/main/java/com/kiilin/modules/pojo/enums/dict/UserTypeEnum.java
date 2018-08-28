package com.kiilin.modules.pojo.enums.dict;

import com.fasterxml.jackson.annotation.JsonValue;
import com.kiilin.common.annotation.EnumDictType;
import com.kiilin.common.config.IDictEnum;

/**
 * 用户类型
 *
 * @author wagk
 */
@EnumDictType(type = "user_type")
public enum UserTypeEnum implements IDictEnum {


    /**
     * ---------------------------------------------------- 华丽的分割线 ----------------------------------------------------
     */
    ;

    /**
     * 值
     */
    private String value;

    /**
     * 描述
     */
    private String desc;

    UserTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return value;
    }

    public UserTypeEnum setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    @JsonValue
    public String getDesc() {
        return desc;
    }

    public UserTypeEnum setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    @Override
    public String toString() {
        return "MenuTypeEnum{" +
                "value='" + value + '\'' +
                ", desc='" + desc + '\'' +
                "} " + super.toString();
    }
}
