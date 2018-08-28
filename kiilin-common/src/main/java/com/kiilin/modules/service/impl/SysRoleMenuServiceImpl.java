package com.kiilin.modules.service.impl;

import com.kiilin.common.abstracts.service.impl.AbstractServiceImpl;
import com.kiilin.modules.dao.SysRoleMenuDao;
import com.kiilin.modules.pojo.dto.SysRoleMenu;
import com.kiilin.modules.service.SysRoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色-菜单（操作）关系表 服务实现类
 *
 * @author wagk
 * @since 2018-07-27
 */
@Service
@Slf4j
public class SysRoleMenuServiceImpl extends AbstractServiceImpl<SysRoleMenuDao, SysRoleMenu> implements SysRoleMenuService {


    @Override
    public Integer deleteByRoleId(String roleId) {
        return baseMapper.deleteByRoleId(roleId);
    }

    @Override
    public List<String> selectRoleMenus(String roleId) {
        return baseMapper.selectRoleMenus(roleId);
    }
}
