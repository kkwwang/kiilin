package com.kiilin.modules.service;

import com.kiilin.common.abstracts.service.AbstractService;
import com.kiilin.modules.dao.SysMenuDao;
import com.kiilin.modules.pojo.dto.SysMenu;
import com.kiilin.modules.pojo.entity.SysMenuEntity;
import com.kiilin.modules.pojo.enums.dict.SysCodeEnum;

import java.util.List;
import java.util.Map;

/**
 * $!{table.comment} 服务类
 *
 * @author wangkai
 * @since 2018-07-24
 */
public interface SysMenuService extends AbstractService<SysMenuDao, SysMenu> {

    /**
     * 根据用户id查询所有权限的菜单
     * @param userId
     * @param types
     * @param sysCode
     * @return
     */
    List<SysMenu> getMenuByUser(String userId, String[] types, SysCodeEnum sysCode);

    /**
     * 查询菜单树
     * @param sysCode
     * @return
     */
    List<SysMenuEntity> selectTree(SysCodeEnum sysCode);

    /**
     * 查询菜单树 无操作节点
     * @param sysCode
     * @return
     */
    List<SysMenuEntity> selectTreeNoneAction(SysCodeEnum sysCode);

    /**
     * 获取shiroconfig中需要的过滤map
     * @return
     */
    Map<String, String> getShiroMenuConfig();


}
