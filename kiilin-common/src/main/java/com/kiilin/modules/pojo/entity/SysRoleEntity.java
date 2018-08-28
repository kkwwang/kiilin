package com.kiilin.modules.pojo.entity;


import com.kiilin.modules.pojo.dto.SysRole;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 角色 扩展
 *
 * @author wagk
 * @since 2018-07-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel("角色 扩展")
public class SysRoleEntity extends SysRole {

    /**
     * 所有用户
     */
    private List<SysUserEntity> users;

    /**
     * 拥有菜单
     */
    private List<SysMenuEntity> menus;

}
