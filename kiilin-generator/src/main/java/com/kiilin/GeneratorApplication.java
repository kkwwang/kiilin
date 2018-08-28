package com.kiilin;

import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.kiilin.genterator.GeneratorConfigEntity;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成代码的控制器
 *
 * @author wangkai
 */
@SpringBootApplication
public class GeneratorApplication implements CommandLineRunner, ApplicationContextAware {

    @Autowired
    GeneratorConfigEntity generator;

    public static void main(String[] args) {
        SpringApplication.run(GeneratorApplication.class, args);
        showdown();
    }


    /**
     * 项目启动完成执行
     *
     * @param strings
     * @throws Exception
     */
    @Override
    public void run(String... strings) {
        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                this.setMap(map);
            }
        };

        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        // 自定义 html 生成
        focList.add(new FileOutConfig("/templates/htmlDiy.html.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return generator.getGlobalConfig().getOutputDir() + "\\html\\" + tableInfo.getEntityPath() + ".html";
            }
        });
        // 自定义 js 生成
        focList.add(new FileOutConfig("/templates/javascriptDiy.js.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return generator.getGlobalConfig().getOutputDir() + "\\js\\" + tableInfo.getEntityPath() + ".js";
            }
        });
        // 自定义 sql 生成
        focList.add(new FileOutConfig("/templates/sql.sql.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return generator.getGlobalConfig().getOutputDir() + "\\sql\\" + tableInfo.getEntityPath() + ".sql";
            }
        });
        cfg.setFileOutConfigList(focList);
        generator.setCfg(cfg);

        // 执行生成
        generator.execute();
    }

    private static ConfigurableApplicationContext context;

    public static void showdown() {
        if (null != context) {
            context.close();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext instanceof ConfigurableApplicationContext) {
            context = (ConfigurableApplicationContext) applicationContext;
        }
    }
}
