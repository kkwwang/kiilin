package com.kiilin.common.shiro;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kiilin.common.redis.RedisKeys;
import com.kiilin.common.redis.RedisUtils;
import com.kiilin.modules.pojo.dto.SysUser;
import com.kiilin.modules.pojo.enums.dict.UserStatusEnum;
import com.kiilin.modules.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 认证
 *
 * @author wangkai
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    SysUserService userService;

    @Autowired
    RedisUtils redisUtils;

    //告诉shiro如何根据获取到的用户信息中的密码和盐值来校验密码
    {
        // 设置用于匹配密码的CredentialsMatcher
        HashedCredentialsMatcher hashMatcher = new HashedCredentialsMatcher();
        hashMatcher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);
        hashMatcher.setHashIterations(16);
        this.setCredentialsMatcher(hashMatcher);
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;


        // 查询用户信息
        SysUser user = userService.selectOne(new EntityWrapper<SysUser>().where("login_name={0}", token.getUsername()).or("mobile={0}", token.getUsername()));

        // 账号不存在
        if (user == null) {
            throw new UnknownAccountException("账号不存在");
        }

        // 账号锁定
        if (!UserStatusEnum.RIGHT.getValue().equals(user.getStatus().getValue())) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
        return info;
    }


    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SysUser user = ShiroUtils.getUser();

        // todo 角色暂未授权 目前暂无需要
        if (null != user) {
            String key = RedisKeys.PERMS_LIST + user.getId();

            // 登录完成后将权限放入redis
            List<String> permsList = (List<String>) redisUtils.get(key, List.class);

            // 权限为空时登出改账号
            if (null == permsList) {
                // 刷新权限
                permsList = userService.queryAllPerms(user.getId());
                // 保存到redis中
                redisUtils.set(key, permsList);
            }

            //用户权限列表
            Set<String> permsSet = new HashSet<>();
            for (String perms : permsList) {
                if (StringUtils.isBlank(perms)) {
                    continue;
                }
                permsSet.addAll(Arrays.asList(perms.trim().split(",")));
            }

            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.setStringPermissions(permsSet);
            return info;
        }
        return null;
    }
}
