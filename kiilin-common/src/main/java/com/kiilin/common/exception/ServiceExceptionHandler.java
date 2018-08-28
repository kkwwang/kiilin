
package com.kiilin.common.exception;

import com.kiilin.common.abstracts.result.ServiceResult;
import com.kiilin.common.util.LogUtils;
import com.kiilin.modules.pojo.enums.DataBaseIndexNameEnum;
import com.kiilin.modules.pojo.enums.ServiceCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 异常处理器 处理 controller层异常
 *
 * @author wangkai
 */
@ControllerAdvice
@Slf4j
public class ServiceExceptionHandler {

    @Value("${spring.profiles.active:prod }")
    String active;

    private static final String SHOW_SYS_ERR_MSG = "dev";

    /**
     * 创建 Pattern 对象
     */
    Pattern r = Pattern.compile("Duplicate entry '(.*)' for key '(.*)'");

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ServiceResult handleRRException(ServiceException e) {
        LogUtils.warn(log, e.getMessage());
        return ServiceResult.error(e);
    }

    /**
     * 数据库已存在数据
     * 注意，如果需要详细提示字段，需要在 下面枚举中定义字段信息 {@link DataBaseIndexNameEnum}
     *
     * @param e
     * @return
     */
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    public ServiceResult handleDuplicateKeyException(DuplicateKeyException e) {
        String message = e.getCause().getMessage();
        // 现在创建 matcher 对象
        Matcher m = r.matcher(message);
        if (m.find()) {
            try {
                String value = m.group(1);
                String msg = m.group(2);

                ServiceCodeEnum codeEnum = ServiceCodeEnum.DATA_IS_EXISTS;
                return ServiceResult.error(
                        codeEnum.getCode(),
                        codeEnum.getMessage(value, DataBaseIndexNameEnum.valueOf(StringUtils.upperCase(msg)).getMessage())
                );
            } catch (Exception ex) {
                return ServiceResult.error("数据库中已存在该记录");
            }
        }

        return ServiceResult.error("数据库中已存在该记录");
    }

    /**
     * 授权不通过
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public ServiceResult handleAuthorizationException(AuthorizationException e) {
        return ServiceResult.error("403", "没有权限，请联系管理员授权");
    }

    /**
     * 系统异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ServiceResult handleException(Exception e) {
        LogUtils.error(log, e.getMessage(), e);
        if (StringUtils.equalsIgnoreCase(SHOW_SYS_ERR_MSG, active)) {
            return ServiceResult.error(e);
        }
        return ServiceResult.error();
    }


}
