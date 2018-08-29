package com.kiilin.modules.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kiilin.common.abstracts.controller.AbstractController;
import com.kiilin.common.abstracts.result.ServiceResult;
import com.kiilin.common.annotation.SysLog;
import com.kiilin.common.shiro.ShiroUtils;
import com.kiilin.common.validator.ValidatorUtils;
import com.kiilin.modules.pojo.dto.SysMenu;
import com.kiilin.modules.pojo.entity.SysMenuEntity;
import com.kiilin.modules.pojo.enums.dict.SysCodeEnum;
import com.kiilin.modules.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * 菜单 前端控制器
 *
 * @author wangkai
 * @since 2018-06-14
 */
@RestController
@RequestMapping("/sysMenu")
@Slf4j
public class SysMenuController extends AbstractController {

    @Autowired
    SysMenuService menuService;


    /**
     * @param q
     * @return
     */
    @RequestMapping("list")
    @RequiresPermissions("sysMenu:list")
    public List<SysMenu> child(SysMenu q) {
        // 调用查询
        List<SysMenu> list = menuService.selectList(new EntityWrapper<>(q));

        // 各渠道顶级菜单
        SysCodeEnum[] values = SysCodeEnum.values();
        for (SysCodeEnum codeEnum : values) {
            SysMenu sysCodeRoot = new SysMenu();
            sysCodeRoot.setId(codeEnum.getValue());
            sysCodeRoot.setMenuName(codeEnum.getDesc() + "根目录");
            sysCodeRoot.setParentId("0");
            sysCodeRoot.setParentIds("0,");
            list.add(sysCodeRoot);
        }

        return list;
    }

    /**
     * 查询单条记录
     *
     * @param id
     * @return
     */
    @RequestMapping("/info")
    @RequiresPermissions("sysMenu:info")
    public ServiceResult info(String id) {

        // 调用查询
        SysMenu info = menuService.selectById(id);
        return ServiceResult.success(info);
    }


    /**
     * 修改或新增 菜单表
     *
     * @param sysMenu
     * @return
     */
    @SysLog("修改或新增 菜单表")
    @PostMapping("/save")
    @RequiresPermissions("sysMenu:save")
    public ServiceResult save(SysMenu sysMenu) {
        // 数据校验
        ValidatorUtils.validateEntity(sysMenu);

        // 调用方法
        boolean result = menuService.insertOrUpdate(sysMenu);
        // 菜单被编辑时，清空用户权限
        ShiroUtils.clearUserPerms();
        // 菜单被编辑时，调整系统shiro权限
        ShiroUtils.updatePermission(menuService.getShiroMenuConfig());

        // 返回结果
        return ServiceResult.success(result);
    }

    /**
     * 删除 菜单表
     *
     * @param id
     * @return
     */
    @SysLog("删除 菜单表")
    @PostMapping("/del")
    @RequiresPermissions("sysMenu:del")
    public ServiceResult del(String id) {
        // 调用方法
        boolean result = menuService.deleteById(id);
        // 菜单被编辑时，清空用户权限
        ShiroUtils.clearUserPerms();
        // 菜单被编辑时，调整系统shiro权限
        ShiroUtils.updatePermission(menuService.getShiroMenuConfig());
        // 返回结果
        return ServiceResult.success(result);

    }

    /**
     * 查询登录用户的菜单
     *
     * @return
     */
    @RequestMapping("/getMenuByUser")
    public ServiceResult getMenuByUser(String[] types, SysCodeEnum sysCode) {
        return ServiceResult.success(menuService.getMenuByUser(getUserId(), types, sysCode));
    }


    /**
     * 查询菜单树
     *
     * @return
     */
    @RequestMapping("/selectTree")
    public ServiceResult selectTree(boolean hasRoot, SysCodeEnum sysCode) {

        List<SysMenuEntity> list = menuService.selectTree(sysCode);
        if (hasRoot) {
            SysMenuEntity root = new SysMenuEntity();
            root.setId(sysCode.getValue());
            root.setMenuName("根目录");
            root.setChildren(list);
            return ServiceResult.success(Arrays.asList(root));
        }
        return ServiceResult.success(list);
    }


    /**
     * 查询菜单树
     *
     * @return
     */
    @RequestMapping("/selectTreeNoneAction")
    public ServiceResult selectTreeNoneAction(boolean hasRoot, SysCodeEnum sysCode) {

        List<SysMenuEntity> list = menuService.selectTreeNoneAction(sysCode);
        if (hasRoot) {
            SysMenuEntity root = new SysMenuEntity();
            root.setMenuName("根目录");
            root.setId(sysCode.getValue());
            root.setChildren(list);
            return ServiceResult.success(Arrays.asList(root));
        }
        return ServiceResult.success(list);
    }
}

