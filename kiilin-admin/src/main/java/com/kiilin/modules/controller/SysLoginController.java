package com.kiilin.modules.controller;


import com.kiilin.common.abstracts.result.ServiceResult;
import com.kiilin.common.annotation.SysLog;
import com.kiilin.common.redis.RedisKeys;
import com.kiilin.common.redis.RedisUtils;
import com.kiilin.common.shiro.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登录相关
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月10日 下午1:15:31
 */
@RestController
@Slf4j
public class SysLoginController {

    @Autowired
    RedisUtils redisUtils;

    /**
     * 登录
     */
    @PostMapping(value = "/login")
    @SysLog("用户登录")
    public ServiceResult login(String loginName, String password) {

        try {
            Subject subject = ShiroUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(loginName, password);
            subject.login(token);

            // 删除权限缓存 防止出现脏数据
            String userId = ShiroUtils.getUserId();
            redisUtils.delete(RedisKeys.PERMS_LIST + userId);
        } catch (UnknownAccountException e) {
            return ServiceResult.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return ServiceResult.error("密码输入有误");
        } catch (LockedAccountException e) {
            return ServiceResult.error(e.getMessage());
        } catch (AuthenticationException e) {
            return ServiceResult.error("账户验证失败");
        }

        return ServiceResult.success();
    }

    /**
     * 退出
     */
    @GetMapping(value = "/logout")
    public ModelAndView logout() {


        // 删除权限缓存 防止出现脏数据
        String userId = ShiroUtils.getUserId();
        redisUtils.delete(RedisKeys.PERMS_LIST + userId);

        ShiroUtils.logout();

        return new ModelAndView("redirect:/");
    }

}
