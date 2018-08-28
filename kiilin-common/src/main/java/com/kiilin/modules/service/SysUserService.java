package com.kiilin.modules.service;

import com.kiilin.common.abstracts.service.AbstractService;
import com.kiilin.modules.dao.SysUserDao;
import com.kiilin.modules.pojo.dto.SysDept;
import com.kiilin.modules.pojo.dto.SysUser;
import com.kiilin.modules.pojo.form.ModifyPasswordForm;

import java.util.List;

/**
 * 用户表 服务类
 *
 * @author wagk
 * @since 2018-07-24
 */
public interface SysUserService extends AbstractService<SysUserDao, SysUser> {

    /**
     * 查询用户拥有的权限集合
     *
     * @param userId 用户id
     * @return 用户关联权限标识
     */
    List<String> queryAllPerms(String userId);

    /**
     * 保存或修改
     * @param sysUser 用户信息
     * @param roleIds 用户角色id
     * @param deptIds 用户部门id
     * @return 执行结果
     */
    boolean insertOrUpdate(SysUser sysUser, String[] roleIds, String[] deptIds);

    /**
     * 查询用户角色
     *
     * @param userId 用户id
     * @return 角色id
     */
    List<String> getUserRoleList(String userId);

    /**
     * 修改密码
     *
     * @param form 修改密码表单
     * @return 执行结果
     */
    boolean modifyPassword(ModifyPasswordForm form);

    /**
     * 查询用户部门
     *
     * @param userId 用户id
     * @return 部门
     */
    List<SysDept> getUserDeptList(String userId);
}
