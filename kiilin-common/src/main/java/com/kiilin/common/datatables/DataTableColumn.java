package com.kiilin.common.datatables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据列的模型
 *
 * @author wangkai
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataTableColumn {

    /**
     * columns 绑定的数据源，由 columns.data 定义
     */
    private String data;

    /**
     * columns 的名字，由 columns.name 定义。
     */
    private String name;

    /**
     * 标记列是否能被搜索,为true代表可以，否则不可以，这个是由 columns.searchable 控制
     */
    private Boolean searchable;

    /**
     * 标记列是否能排序,为 true代表可以，否则不可以，这个是由 columns.orderable 控制
     */
    private Boolean orderable;

    /**
     * 标记具体列的搜索条件
     */
    private DataTableSearch search = new DataTableSearch();
}