package com.kiilin.common.abstracts.controller;

import com.kiilin.common.shiro.ShiroUtils;
import com.kiilin.modules.pojo.dto.SysUser;
import lombok.extern.slf4j.Slf4j;

/**
 * controller 的超类
 *
 * @author wangkai
 */
@Slf4j
public class AbstractController {

    public String getUserId() {
        return ShiroUtils.getUserId();
    }

    public SysUser getUser(){
        return ShiroUtils.getUser();
    }


}
