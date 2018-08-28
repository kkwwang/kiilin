package com.kiilin;

import com.kiilin.common.shiro.ShiroUtils;
import com.kiilin.common.util.EnumDictUtils;
import com.kiilin.common.util.LogUtils;
import com.kiilin.modules.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


/**
 * 启动入口
 *
 * @author wangkai
 */
@Slf4j
@SpringBootApplication
@MapperScan(basePackages = "com.kiilin.**.dao")
public class AdminApplication extends SpringBootServletInitializer implements CommandLineRunner {

    @Autowired
    SysMenuService menuService;

    public static void main(String[] args) {

        SpringApplication.run(AdminApplication.class, args);
    }


    /**
     * war包启动
     *
     * @param application
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        setRegisterErrorPageFilter(false);

        return application.sources(AdminApplication.class);
    }

    /**
     * 项目启动完成执行
     *
     * @param strings
     * @throws Exception
     */
    @Override
    public void run(String... strings) {

        LogUtils.info(log, "项目启动完成，开始注入枚举字典项");
        // 自定义枚举配置 用于查询 数据字典 动态增加枚举项
        EnumDictUtils.enumDict();
        LogUtils.info(log, "注入枚举字典项完成");

        LogUtils.info(log, "项目启动完成，开始刷新shiro权限");
        ShiroUtils.updatePermission(menuService.getShiroMenuConfig());
        LogUtils.info(log, "刷新shiro权限完成");
    }
}
