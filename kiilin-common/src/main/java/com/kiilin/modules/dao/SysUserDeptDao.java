package com.kiilin.modules.dao;

import com.kiilin.common.abstracts.dao.AbstractDao;
import com.kiilin.modules.pojo.dto.SysDept;
import com.kiilin.modules.pojo.dto.SysUserDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 用户-角色关系表 Mapper 接口
 *
 * @author wagk
 * @since 2018-08-20
 */
public interface SysUserDeptDao extends AbstractDao<SysUserDept> {

    /**
     * 根据userId删除已有关系
     *
     * @param userId
     * @return
     */
    int deleteByUserId(@Param("userId") String userId);


    /**
     * 查询用户部门
     *
     * @param userId 用户id
     * @return 部门
     */
    List<SysDept> getUserDeptList(@Param("userId") String userId);

}
