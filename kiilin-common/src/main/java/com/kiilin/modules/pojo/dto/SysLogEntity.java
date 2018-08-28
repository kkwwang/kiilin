package com.kiilin.modules.pojo.dto;

import com.baomidou.mybatisplus.annotations.TableName;
import com.kiilin.common.abstracts.dto.AbstractModel;
import lombok.Data;

import java.io.Serializable;


/**
 * 系统日志
 *
 * @author wagk
 */
@TableName("sys_log")
@Data
public class SysLogEntity extends AbstractModel implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户操作
     */
    private String operation;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 执行时长(毫秒)
     */
    private Long time;

    /**
     * IP地址
     */
    private String ip;


}
