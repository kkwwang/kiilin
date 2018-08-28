package com.kiilin.modules.service;

import com.kiilin.common.abstracts.service.AbstractService;
import com.kiilin.modules.dao.SysUserRoleDao;
import com.kiilin.modules.pojo.dto.SysUserRole;

import java.util.List;

/**
 * 用户-角色关系表 服务类
 *
 * @author wagk
 * @since 2018-07-27
 */
public interface SysUserRoleService extends AbstractService<SysUserRoleDao, SysUserRole> {


    /**
     * 根据userid删除关系
     *
     * @param userId
     * @return
     */
    Integer deleteByUserId(String userId);

    /**
     * 查询用户角色
     *
     * @param userId
     * @return
     */
    List<String> getUserRoleList(String userId);
}
