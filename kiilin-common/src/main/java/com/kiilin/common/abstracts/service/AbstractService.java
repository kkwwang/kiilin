package com.kiilin.common.abstracts.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.kiilin.common.abstracts.dao.AbstractDao;
import com.kiilin.common.datatables.DataTablePager;

import java.util.Map;

/**
 * service的超类
 * M mapper
 * T dto
 * @author wangkai
 */
public interface AbstractService<M extends AbstractDao<T>, T> extends IService<T> {

    /**
     * 获取分页数据模型，自行写sql时需调用
     *
     * @param map
     * @return
     */
    DataTablePager getPager(Map map);

    /**
     * 查询分页
     *
     * @param map 包含查询条件（仅可查询 <code> = </code> 条件、<code> like </code> 条件暂不支持。且必须在T的实体中有该字段）、分页参数的map
     * @return
     */
    DataTablePager<T> selectPage(Map map);

    /**
     * 将实体条件 <code> = </code> 转换为 <code> like </code>
     * @param t 实体 条件中id将被转换为 eq， 已“=”开头的字符串将被转换为 eq，key中包含Id的 将被转换为 eq， 时间类型不被转换，其他非字符串类型将被转换为eq
     * @return
     */
    Wrapper<T> eq2Like(T t);


    /**
     * 该分页方法不再使用 新使用{@link com.kiilin.common.abstracts.service.AbstractService#selectPage(Map Map)}
     *
     * @param page
     * @return
     */
    @Override
    @Deprecated
    Page<T> selectPage(Page<T> page);

    /**
     * 该分页方法不再使用 新使用{@link com.kiilin.common.abstracts.service.AbstractService#selectPage(Map Map)}
     *
     * @param page
     * @param wrapper
     * @return
     */
    @Override
    @Deprecated
    Page<T> selectPage(Page<T> page, Wrapper<T> wrapper);

    /**
     * 该分页方法不再使用 新使用{@link com.kiilin.common.abstracts.service.AbstractService#selectPage(Map Map)}
     *
     * @param page
     * @param wrapper
     * @return
     */
    @Override
    @Deprecated
    Page<Map<String, Object>> selectMapsPage(Page page, Wrapper<T> wrapper);
}
