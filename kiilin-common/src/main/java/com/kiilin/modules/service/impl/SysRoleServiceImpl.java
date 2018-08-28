package com.kiilin.modules.service.impl;

import com.kiilin.common.abstracts.service.impl.AbstractServiceImpl;
import com.kiilin.modules.dao.SysRoleDao;
import com.kiilin.modules.pojo.dto.SysRole;
import com.kiilin.modules.pojo.dto.SysRoleMenu;
import com.kiilin.modules.service.SysRoleMenuService;
import com.kiilin.modules.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色表 服务实现类
 *
 * @author wagk
 * @since 2018-08-09
 */
@Service
@Slf4j
public class SysRoleServiceImpl extends AbstractServiceImpl<SysRoleDao, SysRole> implements SysRoleService {

    @Autowired
    SysRoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleMenus(String roleId) {
        return roleMenuService.selectRoleMenus(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertOrUpdate(SysRole entity, String[] menuIds) {
        // 保存
        boolean result = this.insertOrUpdate(entity);
        String roleId = entity.getId();
        // 删除原有权限
        roleMenuService.deleteByRoleId(roleId);
        // 保存权限
        for (String menuId : menuIds) {
            if(StringUtils.isNotBlank(menuId)){
                SysRoleMenu rm = new SysRoleMenu();
                rm.setRoleId(roleId);
                rm.setMenuId(menuId);
                roleMenuService.insert(rm);
            }
        }

        return result;
    }


}
