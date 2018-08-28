package com.kiilin.modules.pojo.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.kiilin.common.abstracts.dto.AbstractModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * 数据字典
 *
 * @author wagk
 * @since 2018-08-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName("sys_dict")
@ApiModel("数据字典")
public class SysDict extends AbstractModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 类型值
     */
    @TableField("type")
    @ApiModelProperty(value = "类型值")
    private String type;

    /**
     * 字典名称
     */
    @TableField("label")
    @ApiModelProperty(value = "字典名称")
    private String label;

    /**
     * 字典值
     */
    @TableField("value")
    @ApiModelProperty(value = "字典值")
    private String value;

    /**
     * 排序
     */
    @TableField("sort")
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 系统数据标识, 系统数据时不允许修改
     */
    @TableField("sys_flag")
    @ApiModelProperty(value = "系统数据标识, 系统数据时不允许修改")
    private Boolean sysFlag;



}
