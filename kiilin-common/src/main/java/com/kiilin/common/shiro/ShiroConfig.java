package com.kiilin.common.shiro;

import com.kiilin.modules.service.SysMenuService;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Shiro的配置文件
 *
 * @author wangkai
 */
@Configuration
public class ShiroConfig {

    @Autowired
    ShiroConfigEntity entity;

    @Autowired
    SysMenuService sysMenuService;


    /**
     * 自定义Realm
     */
    @Bean
    public Realm realm(
    ) {
        // 创建自己的Realm实例
        UserRealm userRealm = new UserRealm();
        userRealm.setCachingEnabled(true);
        return userRealm;
    }


    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        /**
         * setUsePrefix(false)用于解决一个奇怪的bug。在引入spring aop的情况下。
         * 在@Controller注解的类的方法中加入@RequiresRole等shiro注解，会导致该方法无法映射请求，导致返回404。
         * 加入这项配置能解决这个bug
         */
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        return chainDefinition;
    }


    /**
     * 建立 shiroFilter Bean ---> 设置securityManager并且把请求规则加入Filter过滤链中
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        // 登录地址
        shiroFilter.setLoginUrl(entity.getLoginUrl());
        // 无权限跳转地址
        shiroFilter.setUnauthorizedUrl(entity.getUnauthorizedUrl());
        // 其他过滤器初始化放在项目启动完成后执行
        return shiroFilter;
    }
}
