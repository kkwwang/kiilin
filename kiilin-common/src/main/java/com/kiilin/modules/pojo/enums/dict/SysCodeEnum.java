package com.kiilin.modules.pojo.enums.dict;

import com.fasterxml.jackson.annotation.JsonValue;
import com.kiilin.common.annotation.EnumDictType;
import com.kiilin.common.config.IDictEnum;

/**
 * 系统渠道号
 *
 * @author wagk
 */
@EnumDictType(type = "sys_code")
public enum SysCodeEnum implements IDictEnum {




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

    SysCodeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return value;
    }

    public SysCodeEnum setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    @JsonValue
    public String getDesc() {
        return desc;
    }

    public SysCodeEnum setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    @Override
    public String toString() {
        return "SysCodeEnum{" +
                "value='" + value + '\'' +
                ", desc='" + desc + '\'' +
                "} " + super.toString();
    }
}
