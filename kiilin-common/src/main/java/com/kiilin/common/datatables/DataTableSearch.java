package com.kiilin.common.datatables;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 搜索条件
 *
 * @author wangkai
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataTableSearch {

    /**
     * 标记具体的搜索条件
     */
    private String value;

    /**
     * 特定列的搜索条件是否视为正则表达式， 如果为 true代表搜索的值是作为正则表达式处理，为 false则不是。 注意：通常在服务器模式下对于大数据不执行这样的正则表达式，但这都是自己决定的
     */
    private Boolean regex;
}