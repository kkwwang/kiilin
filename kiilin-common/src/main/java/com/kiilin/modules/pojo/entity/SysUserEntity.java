package com.kiilin.modules.pojo.entity;

import com.kiilin.modules.pojo.dto.SysUser;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 用户 扩展
 *
 * @author wagk
 * @since 2018-07-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel("用户 扩展")
public class SysUserEntity extends SysUser {

    /**
     * 拥有角色
     */
    private List<SysRoleEntity> roles;

    /**
     * 所属部门
     */
    private List<SysDeptEntity> depts;

}
