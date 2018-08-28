package com.kiilin.modules.pojo.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.kiilin.common.abstracts.dto.AbstractModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 字典类型
 *
 * @author wagk
 * @since 2018-07-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_dict_type")
@ApiModel("字典类型")
public class SysDictType extends AbstractModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 类型值
     */
    @TableField("type_value")
    @ApiModelProperty(value = "类型值")
    @NotBlank(message = "类型值不能为空")
    private String typeValue;

    /**
     * 类型名称
     */
    @TableField("type_label")
    @ApiModelProperty(value = "类型名称")
    @NotBlank(message = "类型名称不能为空")
    private String typeLabel;

    /**
     * 排序
     */
    @TableField("sort")
    @ApiModelProperty(value = "排序")
    @NotNull(message = "排序不能为空")
    private Integer sort;

    /**
     * 系统数据标识, 系统数据时不允许修改
     */
    @TableField("sys_flag")
    @ApiModelProperty(value = "系统数据标识, 系统数据时不允许修改")
    private Boolean sysFlag;
}
