package com.kiilin.modules.controller;


import com.kiilin.common.abstracts.controller.AbstractController;
import com.kiilin.common.abstracts.result.ServiceResult;
import com.kiilin.common.annotation.SysLog;
import com.kiilin.common.datatables.DataTablePager;
import com.kiilin.common.shiro.ShiroUtils;
import com.kiilin.common.validator.ValidatorUtils;
import com.kiilin.modules.pojo.dto.SysRole;
import com.kiilin.modules.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 角色表 前端控制器
 *
 * @author wagk
 * @since 2018-08-09
 */
@RestController
@RequestMapping("/sysRole")
@Slf4j
public class SysRoleController extends AbstractController {


    @Autowired
    SysRoleService sysRoleService;

    /**
     * 分页
     *
     * @param model
     * @return
     */
    @RequestMapping("list")
    @RequiresPermissions("sysRole:list")
    public DataTablePager getPage(@RequestBody Map model) {

        // 调用查询
        DataTablePager< SysRole> page = sysRoleService.selectPage(model);
        return page;
    }

    /**
     * 查询全部角色
     *
     * @return
     */
    @RequestMapping("all")
    @RequiresPermissions("sysRole:list")
    public ServiceResult all() {

        // 调用查询
        List<SysRole> list = sysRoleService.selectList(null);
        return ServiceResult.success(list);
    }
    /**
     * 查询单条记录
     *
     * @param id
     * @return
     */
    @RequestMapping("/info")
    @RequiresPermissions("sysRole:info")
    public ServiceResult info(String id) {

        // 调用查询
        SysRole info = sysRoleService.selectById(id);
        return ServiceResult.success(info);
    }


    /**
     * 修改或新增 角色表
     *
     * @param sysRole
     * @return
     */
    @SysLog("修改或新增 角色表")
    @PostMapping("/save")
    @RequiresPermissions("sysRole:save")
    public ServiceResult save(SysRole sysRole, String[] menuids) {
        // 数据校验
        ValidatorUtils.validateEntity(sysRole);

        // 调用方法
        boolean result = sysRoleService.insertOrUpdate(sysRole, menuids);
        // 角色被编辑时，清空用户权限
        ShiroUtils.clearUserPerms();
        // 返回结果
        return ServiceResult.success(result);
    }

    /**
     * 删除 角色表
     *
     * @param id
     * @return
     */
    @SysLog("删除 角色表")
    @PostMapping("/del")
    @RequiresPermissions("sysRole:del")
    public ServiceResult del(String id) {
        // 调用方法
        boolean result = sysRoleService.deleteById(id);
        // 角色被编辑时，清空用户权限
        ShiroUtils.clearUserPerms();
        // 返回结果
        return ServiceResult.success(result);
    }

    /**
     * 查询角色权限
     *
     * @param id
     * @return
     */
    @PostMapping("/selectRoleMenus")
    @RequiresPermissions("sysRole:info")
    public ServiceResult selectRoleMenus(String id) {
        return ServiceResult.success(sysRoleService.selectRoleMenus(id));
    }
}

