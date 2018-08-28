package com.kiilin.common.xss;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;

/**
 * xss 过滤器
 *
 * @author wangkai
 */
@Configuration
@ConfigurationProperties(prefix = "xss")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class XssFilterConfig {

    /**
     * 改写部分请求不过滤
     */
    private String ignoreUrls;

    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addInitParameter("ignoreUrl", ignoreUrls);
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }
}
