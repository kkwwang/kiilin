package com.kiilin.common.datatables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 排序的数据模型
 *
 * @author wangkai
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataTableOrder {


    /**
     * 告诉后台那些列是需要排序的。 i是一个数组索引，对应的是 columns配置的数组，从0开始
     */
    private Integer column;

    /**
     * 告诉后台列排序的方式， desc 降序 asc升序
     */
    private String dir;
}