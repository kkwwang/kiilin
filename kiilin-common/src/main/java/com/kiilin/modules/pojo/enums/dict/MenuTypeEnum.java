package com.kiilin.modules.pojo.enums.dict;

import com.fasterxml.jackson.annotation.JsonValue;
import com.kiilin.common.annotation.EnumDictType;
import com.kiilin.common.config.IDictEnum;

/**
 * 菜单类型
 *
 * @author wagk
 */
@EnumDictType(type = "menu_type")
public enum MenuTypeEnum implements IDictEnum {


    /**
     * 目录
     */
    MENU_DIR("menu_dir", "目录"),

    /**
     * 菜单
     */
    MENU("menu", "菜单"),


    /**
     * 按钮
     */
    ACTION("action", "按钮"),


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

    MenuTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return value;
    }

    public MenuTypeEnum setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    @JsonValue
    public String getDesc() {
        return desc;
    }

    public MenuTypeEnum setDesc(String desc) {
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
