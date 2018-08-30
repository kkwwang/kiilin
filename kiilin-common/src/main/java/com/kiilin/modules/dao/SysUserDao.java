package com.kiilin.modules.dao;

import com.kiilin.common.abstracts.dao.AbstractDao;
import com.kiilin.modules.pojo.dto.SysUser;
import com.kiilin.modules.pojo.entity.SysUserEntity;
import com.kiilin.modules.pojo.form.ModifyPasswordForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * $!{table.comment} Mapper 接口
 *
 * @author wagk
 * @since 2018-07-24
 */
public interface SysUserDao extends AbstractDao<SysUser> {

    /**
     * 查询用户拥有的权限集合
     *
     * @param userId
     * @return
     */
    List<String> queryAllPerms(@Param("userId") String userId);


    /**
     * 修改密码
     *
     * @param form
     * @return
     */
    int modifyPassword(ModifyPasswordForm form);


    /**
     * 查询登录用户
     *
     * @param loginNameOrMobile
     * @return
     */
    SysUserEntity login(@Param("loginNameOrMobile") String loginNameOrMobile);
}
