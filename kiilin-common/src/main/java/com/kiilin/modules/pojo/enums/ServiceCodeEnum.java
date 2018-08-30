package com.kiilin.modules.pojo.enums;

import java.text.MessageFormat;

/**
 * service错误码
 *
 * @author wangkai
 */
public enum ServiceCodeEnum {

    /**
     * 系统异常
     */
    SUCCESS("1000", "执行成功"),
    SYS_ERROR("500", "未知异常，请联系管理员"),


    DATA_IS_EXISTS("1001", "您输入的 \"{0}\" {1} 已存在"),
    PASSWORD_ERROR("1002", "原密码输入有误"),
    SYS_CODE_IS_NULL("1003", "所属系统不能为空"),


    /**
     * ---------------------------------------------------- 华丽的分割线 ----------------------------------------------------
     */
    ;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 重写构造方法
     *
     * @param code
     * @param message
     */
    ServiceCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage(String... args) {
        if (args != null && args.length > 0) {
            return new MessageFormat(message).format(args);
        }
        return message;
    }

    public ServiceCodeEnum setMessageAndReturn(String message) {
        this.message = message;
        return this;
    }


    @Override
    public String toString() {
        return "ServiceCodeEnum{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
