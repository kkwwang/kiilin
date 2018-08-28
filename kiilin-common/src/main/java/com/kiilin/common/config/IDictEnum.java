package com.kiilin.common.config;


import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

/**
 * 用于字典类型前端接受参数的超类接口
 *
 * @author wagk
 */
public interface IDictEnum extends IEnum {

    /**
     * 枚举前端存储值
     */
    Serializable getDesc();
}
