package com.kiilin.modules.service.impl;

import com.kiilin.common.abstracts.service.impl.AbstractServiceImpl;
import com.kiilin.modules.dao.SysDeptDao;
import com.kiilin.modules.pojo.dto.SysDept;
import com.kiilin.modules.pojo.entity.SysDeptEntity;
import com.kiilin.modules.service.SysDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 部门表 服务实现类
 *
 * @author wagk
 * @since 2018-08-20
 */
@Service
@Slf4j
public class SysDeptServiceImpl extends AbstractServiceImpl<SysDeptDao, SysDept> implements SysDeptService {

    @Override
    public List<SysDeptEntity> selectTree() {
        return baseMapper.selectTree(String.valueOf(0));
    }
}
