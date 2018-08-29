package com.kiilin.modules.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kiilin.common.abstracts.controller.AbstractController;
import com.kiilin.common.abstracts.result.ServiceResult;
import com.kiilin.common.annotation.SysLog;
import com.kiilin.common.validator.ValidatorUtils;
import com.kiilin.modules.pojo.dto.SysDept;
import com.kiilin.modules.pojo.entity.SysDeptEntity;
import com.kiilin.modules.service.SysDeptService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * 部门表 前端控制器
 *
 * @author wagk
 * @since 2018-08-20
 */
@Slf4j
@RestController
@RequestMapping("/sysDept")
public class SysDeptController extends AbstractController {


    @Autowired
    SysDeptService sysDeptService;

    /**
     * 查询树
     *
     * @param q
     * @return
     */
    @RequestMapping("list")
    @RequiresPermissions("sysDept:list")
    public List tree(SysDept q) {

        // 调用查询
        List<SysDept> list = sysDeptService.selectList(new EntityWrapper<>(q));
        return list;
    }

    /**
     * 查询单条记录
     *
     * @param id
     * @return
     */
    @RequestMapping("/info")
    @RequiresPermissions("sysDept:info")
    public ServiceResult info(String id) {

        // 调用查询
        SysDept info = sysDeptService.selectById(id);
        return ServiceResult.success(info);
    }


    /**
     * 修改或新增 部门表
     *
     * @param sysDept
     * @return
     */
    @SysLog("修改或新增 部门表")
    @PostMapping("/save")
    @RequiresPermissions("sysDept:save")
    public ServiceResult save(SysDept sysDept) {
        // 数据校验
        ValidatorUtils.validateEntity(sysDept);

        // 调用方法
        boolean result = sysDeptService.insertOrUpdate(sysDept);
        // 返回结果
        return ServiceResult.success(result);
    }

    /**
     * 删除 部门表
     *
     * @param id
     * @return
     */
    @SysLog("删除 部门表")
    @PostMapping("/del")
    @RequiresPermissions("sysDept:del")
    public ServiceResult del(String id) {
        // 调用方法
        boolean result = sysDeptService.deleteById(id);
        // 返回结果
        return ServiceResult.success(result);

    }

    /**
     * 查询菜单树selectTree
     *
     * @return
     */
    @RequestMapping("/selectTree")
    public ServiceResult selectTree(boolean hasRoot) {

        List<SysDeptEntity> list = sysDeptService.selectTree();
        if (hasRoot) {
            SysDeptEntity root = new SysDeptEntity();
            root.setId("0");
            root.setDeptName("根目录");
            root.setChildren(list);
            return ServiceResult.success(Arrays.asList(root));
        }
        return ServiceResult.success(list);
    }

}

