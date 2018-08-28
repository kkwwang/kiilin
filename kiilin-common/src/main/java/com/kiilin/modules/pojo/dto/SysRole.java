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
 * 角色表
 *
 * @author wagk
 * @since 2018-08-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName("sys_role")
@ApiModel("角色表")
public class SysRole extends AbstractModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名
     */
    @TableField("role_name")
    @ApiModelProperty(value = "角色名")
    private String roleName;

    /**
     * 角色 code
     */
    @TableField("role_code")
    @ApiModelProperty(value = "角色 code")
    private String roleCode;

    /**
     * 角色类型
     */
    @TableField("role_type")
    @ApiModelProperty(value = "角色类型")
    private String roleType;

    /**
     * 数据范围
     */
    @TableField("data_scope")
    @ApiModelProperty(value = "数据范围")
    private Integer dataScope;

    /**
     * 是否可用
     */
    @TableField("useable")
    @ApiModelProperty(value = "是否可用")
    private Boolean useable;



}
