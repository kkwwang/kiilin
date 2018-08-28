package com.kiilin.modules.controller;

import com.kiilin.common.shiro.ShiroUtils;
import com.kiilin.modules.pojo.dto.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 页面视图 控制器
 * @author wagk
 */
@Controller
@Slf4j
public class PageViewController {


    @Value("${sys.welcome}")
    String welcome;


    /**
     * 根目录，欢迎页
     *
     * @return
     */
    @RequestMapping(value = {"/", "index", "index.html", "welcome", "welcome.html"})
    public String index() {
        return welcome;
    }

    /**
     * 登录
     *
     * @return
     */
    @RequestMapping(value = {"login.html", "login"})
    public String login() {
        // 进入登录页面，注销上个用户登录信息
        SysUser user = ShiroUtils.getUser();
        if (null != user) {
            ShiroUtils.logout();
        }
        return "login";
    }

    /**
     * modules下页面跳转
     *
     * @param module
     * @param url
     * @return
     */
    @RequestMapping(value = {"modules/{module}/{url}.html", "modules/{module}/{url}"})
    public ModelAndView module(@PathVariable("module") String module, @PathVariable("url") String url, @RequestParam Map model) {
        return new ModelAndView("modules/" + module + "/" + url, model);
    }


    /**
     * 其他下页面跳转
     *
     * @param dir
     * @param url
     * @return
     */
    @RequestMapping(value = {"/{dir}/{url}.html", "/{dir}/{url}"})
    public ModelAndView other(@PathVariable("dir") String dir, @PathVariable("url") String url, @RequestParam Map model) {
        return new ModelAndView(dir + "/" + url, model);
    }


    /**
     * 根目录跳转
     *
     * @param viewName
     * @return
     */
    @RequestMapping(value = {"/{viewName}", "/{viewName}.html"})
    public String viewName(@PathVariable("viewName") String viewName) {
        return viewName;
    }

}
