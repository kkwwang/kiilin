package com.kiilin.modules.pojo.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.kiilin.common.abstracts.dto.AbstractModel;
import com.kiilin.modules.pojo.enums.dict.DeptTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * 部门表
 *
 * @author wagk
 * @since 2018-08-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName("sys_dept")
@ApiModel("部门表")
public class SysDept extends AbstractModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 父节点id
     */
    @TableField("parent_id")
    @ApiModelProperty(value = "父节点id")
    private String parentId;

    /**
     * 所有父节点id，以逗号分隔
     */
    @TableField("parent_ids")
    @ApiModelProperty(value = "所有父节点id，以逗号分隔")
    private String parentIds;


    /**
     * 部门级别
     */
    @TableField("dept_level")
    @ApiModelProperty(value = "部门级别")
    private Integer deptLevel;

    /**
     * 部门名称
     */
    @TableField("dept_name")
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    /**
     * 部门代号
     */
    @TableField("dept_code")
    @ApiModelProperty(value = "部门代号")
    private String deptCode;

    /**
     * 部门类型
     */
    @TableField("dept_type")
    @ApiModelProperty(value = "部门类型")
    private DeptTypeEnum deptType;


}
