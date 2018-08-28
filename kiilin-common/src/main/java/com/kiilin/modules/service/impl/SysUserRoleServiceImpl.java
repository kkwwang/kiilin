package com.kiilin.modules.service.impl;

import com.kiilin.common.abstracts.service.impl.AbstractServiceImpl;
import com.kiilin.modules.dao.SysUserRoleDao;
import com.kiilin.modules.pojo.dto.SysUserRole;
import com.kiilin.modules.service.SysUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户-角色关系表 服务实现类
 *
 * @author wagk
 * @since 2018-07-27
 */
@Service
@Slf4j
public class SysUserRoleServiceImpl extends AbstractServiceImpl<SysUserRoleDao, SysUserRole> implements SysUserRoleService {

    @Override
    public Integer deleteByUserId(String userId) {
        return baseMapper.deleteByUserId(userId);
    }

    @Override
    public List<String> getUserRoleList(String userId) {
        return baseMapper.getUserRoleList(userId);
    }
}
