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
 * 角色-菜单（操作）关系表
 *
 * @author wagk
 * @since 2018-07-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName("sys_role_menu")
@ApiModel("角色-菜单（操作）关系表")
public class SysRoleMenu extends AbstractModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    @TableField("role_id")
    @ApiModelProperty(value = "角色id")
    private String roleId;

    /**
     * 菜单（操作）id
     */
    @TableField("menu_id")
    @ApiModelProperty(value = "菜单（操作）id")
    private String menuId;


}
