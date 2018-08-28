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
 * 用户-角色关系表
 *
 * @author wagk
 * @since 2018-08-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName("sys_user_dept")
@ApiModel("用户-角色关系表")
public class SysUserDept extends AbstractModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableField("user_id")
    @ApiModelProperty(value = "用户id")
    private String userId;

    /**
     * 部门id
     */
    @TableField("dept_id")
    @ApiModelProperty(value = "部门id")
    private String deptId;



}
