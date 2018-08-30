package com.kiilin.modules.pojo.entity;

import com.kiilin.modules.pojo.dto.SysMenu;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;


/**
 * 菜单表 扩展
 *
 * @author wagk
 * @since 2018-07-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel("菜单表 扩展")
public class SysMenuEntity extends SysMenu implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 子菜单集合
     */
    private List<SysMenuEntity> children;
}
