package com.kiilin.modules.service;

import com.kiilin.common.abstracts.service.AbstractService;
import com.kiilin.modules.dao.SysRoleDao;
import com.kiilin.modules.pojo.dto.SysRole;

import java.util.List;

/**
 * 角色表 服务类
 *
 * @author wagk
 * @since 2018-08-09
 */
public interface SysRoleService extends AbstractService<SysRoleDao, SysRole> {


    /**
     * 根据角色id查询菜单
     *
     * @param roleId
     * @return
     */
    List<String> selectRoleMenus(String roleId);

    /**
     * 保存或添加
     *
     * @param entity  数据对象
     * @param menuIds 权限集合
     * @return
     */
    boolean insertOrUpdate(SysRole entity, String[] menuIds);
}
