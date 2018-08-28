package com.kiilin.modules.dao;

import com.kiilin.common.abstracts.dao.AbstractDao;
import com.kiilin.modules.pojo.dto.SysMenu;
import com.kiilin.modules.pojo.entity.SysMenuEntity;
import com.kiilin.modules.pojo.enums.dict.SysCodeEnum;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 菜单 Mapper 接口
 *
 * @author wangkai
 * @since 2018-07-24
 */
public interface SysMenuDao extends AbstractDao<SysMenu> {


    /**
     * 根据用户id查询所有权限的菜单
     * @param userId
     * @param type
     * @param sysCode
     * @return
     */
    List<SysMenu> getMenuByUser(@Param("userId") String userId, @Param("types") String[] type, @Param("sysCode") SysCodeEnum sysCode);

    /**
     * 查询树形
     * @param parentId
     * @param sysCode
     * @return
     */
    List<SysMenuEntity> selectTree(@Param("parentId") String parentId, @Param("sysCode") SysCodeEnum sysCode);


    /**
     * 查询菜单树 无操作节点
     * @param parentId
     * @param sysCode
     * @return
     */
    List<SysMenuEntity> selectTreeNoneAction(@Param("parentId") String parentId, @Param("sysCode") SysCodeEnum sysCode);


}
