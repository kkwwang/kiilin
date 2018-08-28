package com.kiilin.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Freemarker配置
 *
 * @author wangkai
 */
@Configuration
public class FreemarkerConfig {


    @Autowired
    freemarker.template.Configuration configuration;


    @PostConstruct
    public void setSharedVariable() {

        // null 值替换为空字符串
        configuration.setClassicCompatible(true);
    }

}
