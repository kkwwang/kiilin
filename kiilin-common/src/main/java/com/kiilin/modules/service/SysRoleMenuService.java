package com.kiilin.modules.service;

import com.kiilin.common.abstracts.service.AbstractService;
import com.kiilin.modules.dao.SysRoleMenuDao;
import com.kiilin.modules.pojo.dto.SysRoleMenu;

import java.util.List;

/**
 * 角色-菜单（操作）关系表 服务类
 *
 * @author wagk
 * @since 2018-07-27
 */
public interface SysRoleMenuService extends AbstractService<SysRoleMenuDao, SysRoleMenu> {

    /**
     * 删除该角色的关系
     *
     * @param roleId
     * @return
     */
    Integer deleteByRoleId(String roleId);


    /**
     * 根据角色id查询菜单
     *
     * @param roleId
     * @return
     */
    List<String> selectRoleMenus(String roleId);
}
