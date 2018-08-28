package com.kiilin.modules.pojo.enums.dict;

import com.fasterxml.jackson.annotation.JsonValue;
import com.kiilin.common.annotation.EnumDictType;
import com.kiilin.common.config.IDictEnum;

/**
 * 部门类型
 *
 * @author wagk
 */
@EnumDictType(type = "dept_type")
public enum DeptTypeEnum implements IDictEnum {


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

    DeptTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return value;
    }

    public DeptTypeEnum setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    @JsonValue
    public String getDesc() {
        return desc;
    }

    public DeptTypeEnum setDesc(String desc) {
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
