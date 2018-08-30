package com.kiilin.modules.service.impl;

import com.kiilin.common.abstracts.service.impl.AbstractServiceImpl;
import com.kiilin.common.exception.ServiceException;
import com.kiilin.common.shiro.ShiroUtils;
import com.kiilin.modules.dao.SysUserDao;
import com.kiilin.modules.pojo.dto.SysDept;
import com.kiilin.modules.pojo.dto.SysUser;
import com.kiilin.modules.pojo.dto.SysUserDept;
import com.kiilin.modules.pojo.dto.SysUserRole;
import com.kiilin.modules.pojo.entity.SysUserEntity;
import com.kiilin.modules.pojo.enums.ServiceCodeEnum;
import com.kiilin.modules.pojo.form.ModifyPasswordForm;
import com.kiilin.modules.service.SysUserDeptService;
import com.kiilin.modules.service.SysUserRoleService;
import com.kiilin.modules.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户表 服务实现类
 *
 * @author wagk
 * @since 2018-07-24
 */
@Service
@Slf4j
public class SysUserServiceImpl extends AbstractServiceImpl<SysUserDao, SysUser> implements SysUserService {

    @Autowired
    SysUserRoleService userRoleService;

    @Autowired
    SysUserDeptService userDeptService;

    @Override
    public List<String> queryAllPerms(String userId) {
        return baseMapper.queryAllPerms(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertOrUpdate(SysUser sysUser, String[] roleIds, String[] deptIds) {

        // 生成盐
        if (StringUtils.isBlank(sysUser.getSalt())) {
            sysUser.setSalt(ShiroUtils.getUUID());
        }

        // 盐值加密
        if (StringUtils.isNotBlank(sysUser.getPassword())) {
            sysUser.setPassword(ShiroUtils.sha256(sysUser.getPassword(), sysUser.getSalt()));
        }

        // 调用保存
        boolean result = insertOrUpdate(sysUser);
        String userId = sysUser.getId();
        // 保存用户-角色关系
        // 删除原有关系
        userRoleService.deleteByUserId(userId);
        // 保存新的关系数据
        for (String roleId : roleIds) {
            SysUserRole ur = new SysUserRole();
            ur.setRoleId(roleId);
            ur.setUserId(userId);
            userRoleService.insertOrUpdate(ur);
        }

        // 保存用户-部门关系
        // 删除原有关系
        userDeptService.deleteByUserId(userId);
        // 保存新关系
        for (String deptId : deptIds) {
            SysUserDept ud = new SysUserDept();
            ud.setDeptId(deptId);
            ud.setUserId(userId);
            userDeptService.insertOrUpdate(ud);
        }

        return result;
    }

    @Override
    public List<String> getUserRoleList(String userId) {
        return userRoleService.getUserRoleList(userId);
    }

    @Override
    public boolean modifyPassword(ModifyPasswordForm form) {

        // 取出登录用户
        SysUser user = ShiroUtils.getUser();

        // 修改的用户id
        form.setUserId(user.getId());

        // 原密码盐加密
        form.setPassword(ShiroUtils.sha256(form.getPassword(), user.getSalt()));

        // 新密码盐加密
        form.setNewPassword(ShiroUtils.sha256(form.getNewPassword(), user.getSalt()));

        int result = baseMapper.modifyPassword(form);

        // 原密码输入有误
        if (result == 0) {
            throw new ServiceException(ServiceCodeEnum.PASSWORD_ERROR);
        }

        return retBool(result);
    }

    @Override
    public List<SysDept> getUserDeptList(String userId) {
        return userDeptService.getUserDeptList(userId);
    }


    @Override
    public SysUserEntity login(String loginNameOrMobile) {
        return baseMapper.login(loginNameOrMobile);
    }
}
