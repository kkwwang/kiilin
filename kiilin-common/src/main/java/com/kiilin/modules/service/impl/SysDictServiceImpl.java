package com.kiilin.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kiilin.common.abstracts.service.impl.AbstractServiceImpl;
import com.kiilin.common.datatables.DataTablePager;
import com.kiilin.modules.dao.SysDictDao;
import com.kiilin.modules.pojo.dto.SysDict;
import com.kiilin.modules.service.SysDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 数据字典 服务实现类
 *
 * @author wagk
 * @since 2018-07-24
 */
@Service
@Slf4j
public class SysDictServiceImpl extends AbstractServiceImpl<SysDictDao, SysDict> implements SysDictService {


    @Override
    public List<SysDict> selectByType(String type) {
        // 查询条件
        SysDict query = new SysDict();
        query.setType(type);

        // 调用查询
        List<SysDict> list = baseMapper.selectList(new EntityWrapper<>(query));
        return list;
    }

    @Override
    public DataTablePager<SysDict> selectPageBySql(Map map) {
        // 获取datatables数据模型
        DataTablePager<SysDict> pager = getPager(map);
        // 调用分页
        List<SysDict> list = baseMapper.selectPageBySql(pager.getPage(), map);
        // 放入结果集
        pager.setData(list);
        return pager;
    }
}
