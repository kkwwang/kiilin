package com.kiilin.modules.controller;


import com.kiilin.common.abstracts.controller.AbstractController;
import com.kiilin.common.abstracts.result.ServiceResult;
import com.kiilin.common.annotation.SysLog;
import com.kiilin.common.datatables.DataTablePager;
import com.kiilin.common.shiro.ShiroUtils;
import com.kiilin.common.validator.ValidatorUtils;
import com.kiilin.modules.pojo.dto.SysUser;
import com.kiilin.modules.pojo.form.ModifyPasswordForm;
import com.kiilin.modules.service.SysUserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 系统用户表 前端控制器
 *
 * @author wagk
 * @since 2018-06-14
 */
@RestController
@RequestMapping("/sysUser")
@Api(tags = "用户管理")
@Slf4j
public class SysUserController extends AbstractController {

    @Autowired
    SysUserService userService;

    /**
     * 分页
     *
     * @param model
     * @return
     */
    @RequestMapping("list")
    @RequiresPermissions("sysUser:list")
    public DataTablePager getPage(@RequestBody Map model) {

        // 调用查询
        DataTablePager<SysUser> page = userService.selectPage(model);
        return page;
    }

    /**
     * 查询单条记录
     *
     * @param id
     * @return
     */
    @RequestMapping("/info")
    @RequiresPermissions("sysUser:info")
    public ServiceResult info(String id) {

        // 调用查询
        SysUser info = userService.selectById(id);
        return ServiceResult.success(info);
    }


    /**
     * 修改或新增 用户表
     *
     * @param sysUser
     * @return
     */
    @SysLog("修改或新增 用户表")
    @PostMapping("/save")
    @RequiresPermissions("sysUser:save")
    public ServiceResult save(SysUser sysUser, String[] roleIds, String[] deptIds) {
        // 数据校验
        ValidatorUtils.validateEntity(sysUser);

        // 调用方法
        boolean result = userService.insertOrUpdate(sysUser, roleIds, deptIds);
        // 返回结果
        return ServiceResult.success(result);
    }

    /**
     * 删除 用户表
     *
     * @param id
     * @return
     */
    @SysLog("删除 用户表")
    @PostMapping("/del")
    @RequiresPermissions("sysUser:del")
    public ServiceResult del(String id) {
        // 调用方法
        boolean result = userService.deleteById(id);
        // 返回结果
        return ServiceResult.success(result);
    }

    @RequestMapping("/getLoginUser")
    public ServiceResult getLoginUser() {
        return ServiceResult.success().setData(ShiroUtils.getUser());
    }


    @RequestMapping("/getUserRoleList")
    public ServiceResult getUserRoleList(String userId) {
        return ServiceResult.success(userService.getUserRoleList(userId));
    }


    @RequestMapping("/getUserDeptList")
    public ServiceResult getUserDeptList(String userId) {
        return ServiceResult.success(userService.getUserDeptList(userId));
    }

    @PostMapping("/modifyPassword")
    public ServiceResult modifyPassword(ModifyPasswordForm form) {
        // 数据校验
        ValidatorUtils.validateEntity(form);

        return ServiceResult.success(userService.modifyPassword(form));
    }


}

