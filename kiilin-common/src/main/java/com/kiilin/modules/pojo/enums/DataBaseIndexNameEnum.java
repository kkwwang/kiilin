package com.kiilin.modules.pojo.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 数据库唯一索引枚举（用于校验数据唯一验证）
 * <p>
 * 名称为 数据库中定义的唯一索引的key message为该字段的字段名
 * 实际返回到前端的 {@link ServiceCodeEnum.DATA_IS_EXISTS}
 * @author wagk
 */
@SuppressWarnings("JavadocReference")
public enum DataBaseIndexNameEnum {


    /**
     * -------------------- 用户表 --------------------
     */
    USER_LOGIN_NAME_KEY("登录名"),
    USER_MOBILE_KEY("手机号"),
    USER_EMAIL_KEY("邮箱"),

    /**
     * -------------------- 字典类型表 --------------------
     */
    DICT_TYPE_KEY("字典类型"),

    /**
     * -------------------- 字典表 --------------------
     */
    DICT_TYPE_VALUE_KEY("类型字典值"),
    DICT_TYPE_LABEL_KEY("类型字典名称"),

    /**
     * -------------------- 角色表 --------------------
     */
    ROLE_CODE_KEY("角色代号"),
    ROLE_NAME_KEY("角色名称"),

    /**
     * -------------------- 角色-菜单关系数据表 --------------------
     */
    ROLE_MENU_KEY("角色-菜单关系数据"),

    /**
     * -------------------- 用户-角色关系数据表 --------------------
     */
    USER_ROLE_KEY("用户-角色关系数据"),

    /**
     * -------------------- 部门表 --------------------
     */
    DEPT_CODE_KEY("部门代号"),
    DEPT_NAME_KEY("部门名称"),





    /**
     * ---------------------------------------------------- 华丽的分割线 ----------------------------------------------------
     */
    ;


    /**
     * 错误信息
     */
    private String message;

    /**
     * 重写构造方法
     *
     * @param message
     */
    DataBaseIndexNameEnum(String message) {
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

    public DataBaseIndexNameEnum setMessageAndReturn(String message) {
        if(StringUtils.isNotBlank(message)){
            this.message = message.trim();
        }
        return this;
    }


    @Override
    public String toString() {
        return "DataBaseIndexNameEnum{" +
                "message='" + message + '\'' +
                "} " + super.toString();
    }

}
