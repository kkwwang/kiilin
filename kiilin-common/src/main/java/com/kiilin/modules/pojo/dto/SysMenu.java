package com.kiilin.modules.pojo.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.kiilin.common.abstracts.dto.AbstractModel;
import com.kiilin.modules.pojo.enums.dict.MenuTypeEnum;
import com.kiilin.modules.pojo.enums.dict.SysCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * 菜单表
 *
 * @author wagk
 * @since 2018-08-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@TableName("sys_menu")
@ApiModel("菜单表")
public class SysMenu extends AbstractModel implements Serializable {

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
     * 菜单名称
     */
    @TableField("menu_name")
    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    /**
     * 菜单级别
     */
    @TableField("menu_level")
    @ApiModelProperty(value = "菜单级别")
    private Integer menuLevel;

    /**
     * 菜单类型（1-链接，2-权限） 字典表维护
     */
    @TableField("menu_type")
    @ApiModelProperty(value = "菜单类型（MENU-链接，ACTION-权限） 字典表维护")
    private MenuTypeEnum menuType;

    /**
     * 链接
     */
    @TableField("menu_href")
    @ApiModelProperty(value = "链接")
    private String menuHref;

    /**
     * 目标
     */
    @TableField("menu_target")
    @ApiModelProperty(value = "目标")
    private String menuTarget;

    /**
     * 图标
     */
    @TableField("menu_icon")
    @ApiModelProperty(value = "图标")
    private String menuIcon;

    /**
     * 权限标识
     */
    @TableField("permission")
    @ApiModelProperty(value = "权限标识")
    private String permission;

    /**
     * 是否显示
     */
    @TableField("show_flag")
    @ApiModelProperty(value = "是否显示")
    private Boolean showFlag;

    /**
     * 排序
     */
    @TableField("sort")
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 归属系统（pc:主导航菜单、app:APP菜单） 字典表维护
     */
    @TableField("sys_code")
    @ApiModelProperty(value = "归属系统（pc:主导航菜单、app:APP菜单） 字典表维护")
    private SysCodeEnum sysCode;



}
