package com.kiilin.modules.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.kiilin.common.abstracts.dao.AbstractDao;
import com.kiilin.modules.pojo.dto.SysDict;

import java.util.List;
import java.util.Map;


/**
 * $!{table.comment} Mapper 接口
 *
 * @author wagk
 * @since 2018-07-24
 */
public interface SysDictDao extends AbstractDao<SysDict> {


    /**
     * 自写sql分页demo
     *
     * @param page
     * @param map
     * @return
     */
    List<SysDict> selectPageBySql(Page page, Map map);

}
