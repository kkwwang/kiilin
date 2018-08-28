package com.kiilin.common.datatables;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.plugins.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kiilin.common.xss.SQLFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * datatables 的数据模型
 * 已支持功能 -- 分页/排序
 * <p>
 * 使用该模型接收
 * 后台需要：1.  @RequestBody 注解
 * 前台需要：ajax 设置
 * <code>
 * ......... 1.  contentType: "application/json"
 * ......... 2.  type: "POST",
 * ......... 3.  data:function (data) {
 * .................. return JSON.stringify(data);
 * ............. }
 * </code>
 *
 * @author wangkai
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DataTablePager<T> {

    private static final String ORDER_ASC = "asc";
    private static final String ORDER_DESC = "desc";

    private static final long serialVersionUID = 1L;

    /**
     * 必要。绘制计数器。这个是用来确保Ajax从服务器返回的是对应的（Ajax是异步的，因此返回的顺序是不确定的）。 要求在服务器接收到此参数后再返回
     */
    private Integer draw;

    /**
     * 必要。即没有过滤的记录数（数据库里总共记录数）
     */
    private Long recordsTotal;

    /**
     * 必要。滤后的记录数（如果有接收到前台的过滤条件，则返回的是过滤后的记录数）
     */
    private Long recordsFiltered;

    /**
     * 必要。表中中需要显示的数据。这是一个对象数组，也可以只是数组，区别在于 纯数组前台就不需要用 columns绑定数据，会自动按照顺序去显示 ，而对象数组则需要使用 columns绑定数据才能正常显示。 注意这个 data的名称可以由  ajax 的 ajax.dataSrc 控制
     */
    private List<T> data = new ArrayList<>();

    /**
     * 可选。你可以定义一个错误来描述服务器出了问题后的友好提示
     */
    private String error;

    /**
     * mybatis-plus分页参数
     */
    @JsonIgnore
    @JSONField(deserialize = false, serialize = false)
    private Page<T> page;

    /**
     * 第一条数据的起始位置，比如0代表第一条数据
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer start;

    /**
     * 告诉服务器每页显示的条数，这个数字会等于返回的 data集合的记录数，可能会大于因为服务器可能没有那么多数据。这个也可能是-1，代表需要返回全部数据(尽管这个和服务器处理的理念有点违背)
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer length;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<DataTableColumn> columns = new ArrayList<>();

    /**
     * 全局的搜索条件
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private DataTableSearch search;

    /**
     * 排序条件
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<DataTableOrder> order = new ArrayList<>();

    private T query;


    /**
     * 获取mybatis-plus 分页数据模型
     *
     * @return
     */
    public Page<T> getPage() {
        int currPage = (this.start / this.length) + 1;

        this.page = new Page<>(currPage, this.length);
        this.page.setDescs(new ArrayList<>(1));
        this.page.setAscs(new ArrayList<>(1));

        // 排序
        if (CollectionUtils.isNotEmpty(this.order)) {
            // 设置排序字段
            this.order.forEach(orderItem -> {
                String dir = orderItem.getDir();
                DataTableColumn orderColumn = this.columns.get(orderItem.getColumn());
                // 取列名
                String columnName = null;
                if (null != orderColumn) {
                    columnName = orderColumn.getData();
                    // data如果为空，取name
                    if (StringUtils.isBlank(columnName)) {
                        columnName = orderColumn.getName();
                    }
                }

                if (StringUtils.isNotBlank(columnName) && StringUtils.isNotBlank(dir)) {
                    // 防止SQL注入（因为 columnName 是通过拼接SQL实现排序的，会有SQL注入风险）
                    columnName = SQLFilter.sqlInject(columnName);
                    // 判断排序
                    if (StringUtils.equalsIgnoreCase(ORDER_ASC, dir)) {
                        this.page.getAscs().add(columnName);
                    } else if (StringUtils.equalsIgnoreCase(ORDER_DESC, dir)) {
                        this.page.getDescs().add(columnName);
                    }
                }
            });
        }
        return this.page;
    }

    /**
     * 查询完成调用
     *
     * @param list
     */
    public void setData(List<T> list) {
        if(null == this.page){
            this.page = new Page<>();
        }
        this.data = list;
        this.page.setRecords(list);
        this.recordsTotal = this.page.getTotal();
        this.recordsFiltered = this.page.getTotal();
    }
}