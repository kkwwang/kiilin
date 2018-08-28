package com.kiilin.modules.dao;

import com.kiilin.common.abstracts.dao.AbstractDao;
import com.kiilin.modules.pojo.dto.SysRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 角色-菜单（操作）关系表 Mapper 接口
 *
 * @author wagk
 * @since 2018-07-27
 */
public interface SysRoleMenuDao extends AbstractDao<SysRoleMenu> {


    /**
     * 删除该角色的关系
     *
     * @param roleId
     * @return
     */
    Integer deleteByRoleId(@Param("roleId") String roleId);


    /**
     * 根据角色id查询菜单
     *
     * @param roleId
     * @return
     */
    List<String> selectRoleMenus(@Param("roleId") String roleId);
}
