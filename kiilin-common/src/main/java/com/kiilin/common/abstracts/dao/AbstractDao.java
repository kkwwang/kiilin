package com.kiilin.common.abstracts.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;


/**
 * mapper 父类，注意这个类不要让 mp 扫描到！！
 * T dto
 *
 * @author wangkai
 */
public interface AbstractDao<T> extends BaseMapper<T> {
    // 这里可以放一些公共的方法

}
