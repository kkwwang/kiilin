package com.kiilin.common.exception;

import com.kiilin.modules.pojo.enums.ServiceCodeEnum;

/**
 * 自定义异常
 *
 * @author wangkai
 */
public class ServiceException extends RuntimeException {


    /**
     * 错误码枚举
     */
    private ServiceCodeEnum serviceCodeEnum;

    private String code;

    public ServiceException(ServiceCodeEnum serviceCodeEnum) {
        super(serviceCodeEnum.getMessage());
        this.serviceCodeEnum = serviceCodeEnum;
    }

    public ServiceException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public ServiceException(String msg) {
        super(msg);
        this.serviceCodeEnum = ServiceCodeEnum.SYS_ERROR.setMessageAndReturn(msg);
    }

    public ServiceException(String msg, Throwable e) {
        super(msg, e);
    }

    public ServiceException(Exception e) {
        super(e);
        this.serviceCodeEnum = ServiceCodeEnum.SYS_ERROR.setMessageAndReturn(e.getMessage());
    }

    public ServiceCodeEnum getServiceCodeEnum() {
        return serviceCodeEnum;
    }

    public String getCode() {
        return code;
    }

    public ServiceException setCode(String code) {
        this.code = code;
        return this;
    }
}
