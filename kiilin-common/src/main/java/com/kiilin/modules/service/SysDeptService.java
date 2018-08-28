package com.kiilin.modules.service;

import com.kiilin.common.abstracts.service.AbstractService;
import com.kiilin.modules.dao.SysDeptDao;
import com.kiilin.modules.pojo.dto.SysDept;
import com.kiilin.modules.pojo.entity.SysDeptEntity;

import java.util.List;

/**
 * 部门表 服务类
 *
 * @author wagk
 * @since 2018-08-20
 */
public interface SysDeptService extends AbstractService<SysDeptDao, SysDept> {

    /**
     * 查询部门树
     *
     * @return
     */
    List<SysDeptEntity> selectTree();

}
