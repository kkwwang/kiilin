package com.kiilin.common.shiro;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * shiro 自定义配置接收类
 *
 * @author wangkai
 */
@Data
@Component
@ConfigurationProperties(prefix = "shiro")
public class ShiroConfigEntity {


    /**
     * 登录地址
     */
    private String loginUrl = "/login.html";

    /**
     * 登出
     */
    private String logoutUrl = "/logout";


    /**
     * 登录成功访问
     */
    private String successUrl = "/";

    /**
     * 无权限访问
     */
    private String unauthorizedUrl = "/403";

    /**
     * 其他配置
     */
    private Other other = new Other();

    @Data
    public class Other {

        /**
         * 可匿名访问的url集合
         */
        private List<String> anon = new ArrayList<>();

        /**
         * 必须登录访问的url集合
         */
        private List<String> authc = new ArrayList<>();
    }
}
