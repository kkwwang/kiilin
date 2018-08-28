package com.kiilin.modules.dao;

import com.kiilin.common.abstracts.dao.AbstractDao;
import com.kiilin.modules.pojo.dto.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 用户-角色关系表 Mapper 接口
 *
 * @author wagk
 * @since 2018-07-27
 */
public interface SysUserRoleDao extends AbstractDao<SysUserRole> {

    /**
     * 根据userid删除关系
     * @param userId
     * @return
     */
    Integer deleteByUserId(@Param("userId") String userId);


    /**
     * 查询用户角色
     *
     * @param userId
     * @return
     */
    List<String> getUserRoleList(@Param("userId") String userId);
}
