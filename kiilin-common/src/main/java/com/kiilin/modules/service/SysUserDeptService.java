package com.kiilin.modules.service;

import com.kiilin.common.abstracts.service.AbstractService;
import com.kiilin.modules.dao.SysUserDeptDao;
import com.kiilin.modules.pojo.dto.SysDept;
import com.kiilin.modules.pojo.dto.SysUserDept;

import java.util.List;

/**
 * 用户-角色关系表 服务类
 *
 * @author wagk
 * @since 2018-08-20
 */
public interface SysUserDeptService extends AbstractService<SysUserDeptDao, SysUserDept> {

    /**
     * 根据userId删除已有关系
     *
     * @param userId
     * @return
     */
    boolean deleteByUserId(String userId);


    /**
     * 查询用户部门
     *
     * @param userId 用户id
     * @return 部门
     */
    List<SysDept> getUserDeptList(String userId);

}
