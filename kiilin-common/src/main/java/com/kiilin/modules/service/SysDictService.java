package com.kiilin.modules.service;

import com.kiilin.common.abstracts.service.AbstractService;
import com.kiilin.common.datatables.DataTablePager;
import com.kiilin.modules.dao.SysDictDao;
import com.kiilin.modules.pojo.dto.SysDict;

import java.util.List;
import java.util.Map;

/**
 * $!{table.comment} 服务类
 *
 * @author wagk
 * @since 2018-07-24
 */
public interface SysDictService extends AbstractService<SysDictDao, SysDict> {


    /**
     * 根据类型查询字典
     *
     * @param type
     * @return
     */
    List<SysDict> selectByType(String type);

    /**
     * 自写sql分页demo
     *
     * @param map
     * @return
     */
    DataTablePager<SysDict> selectPageBySql(Map map);
}
