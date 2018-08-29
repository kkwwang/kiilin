package com.kiilin.common.abstracts.result;


import com.kiilin.common.exception.ServiceException;
import com.kiilin.modules.pojo.enums.ServiceCodeEnum;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;


/**
 * 返回的结果集
 *
 * @author wangkai
 */
public class ServiceResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 执行是否成功
     */
    private boolean success;

    /**
     * 代码
     */
    private String code;

    /**
     * 描述
     */
    private String error;

    /**
     * 返回数据
     */
    private Object data;

    /**
     * 改写构造器
     */
    private ServiceResult(boolean success) {
        if (success) {
            this.code = ServiceCodeEnum.SUCCESS.getCode();
            this.error = ServiceCodeEnum.SUCCESS.getMessage();
            this.success = true;
        } else {
            this.code = ServiceCodeEnum.SYS_ERROR.getCode();
            this.error = ServiceCodeEnum.SYS_ERROR.getMessage();
            this.success = false;
        }
    }


    /**
     * 成功 不返回数据
     *
     * @return
     */
    public static ServiceResult success() {
        return new ServiceResult(true);
    }

    /**
     * 成功
     *
     * @param obj data
     * @return
     */
    public static ServiceResult success(Object obj) {
        return success().setData(obj);
    }


    /**
     * 系统异常
     *
     * @return
     */
    public static ServiceResult error() {
        return new ServiceResult(false);
    }

    /**
     * 获取结果,系统异常 -- 定义错误信息
     *
     * @param message
     * @return
     */
    public static ServiceResult error(String message) {
        return error().setCode(ServiceCodeEnum.SYS_ERROR.getCode()).setError(message);
    }

    /**
     * 获取结果 -- 新建result
     *
     * @param code
     * @param message
     * @return
     */
    public static ServiceResult error(String code, String message) {
        return error().setCode(code).setError(message);
    }


    /**
     * 根据自定义异常获取result
     *
     * @param se
     * @return
     */
    public static ServiceResult error(ServiceException se) {
        if (null == se.getServiceCodeEnum()) {
            return error(se.getCode(), se.getMessage());
        }
        return error(se.getServiceCodeEnum());
    }

    /**
     * 根据异常获取result
     *
     * @param e
     * @return
     */
    public static ServiceResult error(Exception e) {

        if (e instanceof ServiceException) {
            return error((ServiceException) e);
        }
        // 异常栈信息为空
        if (StringUtils.isBlank(e.getMessage())) {
            return error(ServiceCodeEnum.SYS_ERROR);
        }
        return error(ServiceCodeEnum.SYS_ERROR.getCode(), e.getMessage());
    }


    /**
     * 获取结果 -- 新建result
     *
     * @param err
     * @return
     */
    public static ServiceResult error(ServiceCodeEnum err) {
        return error(err.getCode(), err.getMessage());
    }


    public boolean isSuccess() {
        return success;
    }

    public ServiceResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getCode() {
        return code;
    }

    public ServiceResult setCode(String code) {
        this.code = code;
        return this;
    }

    public String getError() {
        return error;
    }

    private ServiceResult setError(String error) {
        this.error = error;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ServiceResult setData(Object data) {
        this.data = data;
        return this;
    }

}
