
package com.kiilin.common.shiro;

import com.alibaba.fastjson.JSON;
import com.kiilin.common.redis.RedisKeys;
import com.kiilin.common.redis.RedisUtils;
import com.kiilin.common.util.LogUtils;
import com.kiilin.modules.pojo.dto.SysUser;
import com.kiilin.modules.pojo.entity.SysUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Shiro工具类
 * @author wangkai
 */
@Component
@Slf4j
public class ShiroUtils {

    /**
     * 加密算法
     */
    public final static String HASH_ALGORITHM_NAME = "SHA-256";
    /**
     * 循环次数
     */
    public final static int HASH_ITERATIONS = 16;


    @Autowired
    RedisUtils redisUtils;

    @Autowired
    ShiroFilterFactoryBean filterFactoryBean;

    private static ShiroUtils shiroUtils;

    /**
     * 解决静态方法注入问题
     */
    @PostConstruct
    public void init() {
        shiroUtils = this;
        shiroUtils.redisUtils = this.redisUtils;
        shiroUtils.filterFactoryBean = this.filterFactoryBean;
    }


    public static String sha256(String password, String salt) {
        return new SimpleHash(HASH_ALGORITHM_NAME, password, salt, HASH_ITERATIONS).toString();
    }

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static SysUserEntity getUser() {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if(null == principal){
            return null;
        }
        String string = JSON.toJSONString(principal);
        SysUserEntity user = JSON.parseObject(string, SysUserEntity.class);
        // SysUser user = (SysUserEntity) principal;
        return user;
    }

    public static String getUserId() {
        SysUser user = getUser();
        if (null != user) {
            return user.getId();

        }
        return null;
    }

    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    public static String getUUID() {

        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * 删除用户权限缓存
     */
    public static void clearUserPerms() {

        String key = RedisKeys.PERMS_LIST + "*";
        Set keys = shiroUtils.redisUtils.keys(key);
        shiroUtils.redisUtils.delete(keys);
    }


    /**
     * 动态更新新的权限
     *
     * @param filterMap
     */
    public static synchronized void updatePermission(Map<String, String> filterMap) {

        AbstractShiroFilter shiroFilter = null;
        try {
            shiroFilter = (AbstractShiroFilter) shiroUtils.filterFactoryBean.getObject();

            // 获取过滤管理器
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
            DefaultFilterChainManager filterManager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

            //清空拦截管理器中的存储
            filterManager.getFilterChains().clear();
            /*
            清空拦截工厂中的存储,如果不清空这里,还会把之前的带进去
            ps:如果仅仅是更新的话,可以根据这里的 map 遍历数据修改,重新整理好权限再一起添加
             */
            shiroUtils.filterFactoryBean.getFilterChainDefinitionMap().clear();

            // 相当于新建的 map, 因为已经清空了
            Map<String, String> chains = shiroUtils.filterFactoryBean.getFilterChainDefinitionMap();
            //把修改后的 map 放进去
            chains.putAll(filterMap);

            //这个相当于是全量添加
            for (Map.Entry<String, String> entry : filterMap.entrySet()) {
                //要拦截的地址
                String url = entry.getKey().trim().replace(" ", "");
                //地址持有的权限
                String chainDefinition = entry.getValue().trim().replace(" ", "");
                //生成拦截
                filterManager.createChain(url, chainDefinition);
            }
        } catch (Exception e) {
            LogUtils.error(log, "updatePermission error,filterMap=" + filterMap, e);
        }
    }
}
