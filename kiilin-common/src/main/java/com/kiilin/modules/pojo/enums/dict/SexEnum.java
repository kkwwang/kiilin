package com.kiilin.modules.pojo.enums.dict;

import com.fasterxml.jackson.annotation.JsonValue;
import com.kiilin.common.config.IDictEnum;

/**
 * 性别
 *
 * @author wagk
 */
//@EnumDictType(type = "sex") 此处无法引用 否则会报类型转换异常
public enum SexEnum implements IDictEnum {


    /**
     * 男
     */
    NAN(true, "男"),

    /**
     * 女
     */
    NV(false, "女"),


    /**
     * ---------------------------------------------------- 华丽的分割线 ----------------------------------------------------
     */
    ;

    /**
     * 值
     */
    private Boolean value;

    /**
     * 描述
     */
    private String desc;

    SexEnum(Boolean value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    public SexEnum setValue(Boolean value) {
        this.value = value;
        return this;
    }

    @Override
    @JsonValue
    public String getDesc() {
        return desc;
    }

    public SexEnum setDesc(String desc) {
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
