package com.kiilin.modules.pojo.entity;

import com.kiilin.modules.pojo.dto.SysDept;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * 部门表 扩展
 *
 * @author wagk
 * @since 2018-07-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel("部门表 扩展")
public class SysDeptEntity extends SysDept {

    /**
     * 子部门
     */
    List<SysDeptEntity> children;

    /**
     * 部门人员
     */
    List<SysUserEntity> users;
}
