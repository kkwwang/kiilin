package com.kiilin.modules.service.impl;

import com.kiilin.common.abstracts.service.impl.AbstractServiceImpl;
import com.kiilin.modules.dao.SysUserDeptDao;
import com.kiilin.modules.pojo.dto.SysDept;
import com.kiilin.modules.pojo.dto.SysUserDept;
import com.kiilin.modules.service.SysUserDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 用户-角色关系表 服务实现类
 *
 * @author wagk
 * @since 2018-08-20
 */
@Service
@Slf4j
public class SysUserDeptServiceImpl extends AbstractServiceImpl<SysUserDeptDao, SysUserDept> implements SysUserDeptService {

    @Override
    public boolean deleteByUserId(String userId) {
        return retBool(baseMapper.deleteByUserId(userId));
    }

    @Override
    public List<SysDept> getUserDeptList(String userId) {
        return baseMapper.getUserDeptList(userId);
    }
}
