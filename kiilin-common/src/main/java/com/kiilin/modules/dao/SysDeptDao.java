package com.kiilin.modules.dao;

import com.kiilin.common.abstracts.dao.AbstractDao;
import com.kiilin.modules.pojo.dto.SysDept;
import com.kiilin.modules.pojo.entity.SysDeptEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 部门表 Mapper 接口
 *
 * @author wagk
 * @since 2018-08-20
 */
public interface SysDeptDao extends AbstractDao<SysDept> {

    /**
     * 查询树形
     *
     * @param parentId
     * @return
     */
    List<SysDeptEntity> selectTree(@Param("parentId") String parentId);
}
