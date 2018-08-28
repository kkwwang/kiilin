package com.kiilin.modules.controller;


import com.kiilin.common.abstracts.controller.AbstractController;
import com.kiilin.common.abstracts.result.ServiceResult;
import com.kiilin.common.annotation.SysLog;
import com.kiilin.common.datatables.DataTablePager;
import com.kiilin.common.util.EnumDictUtils;
import com.kiilin.common.validator.ValidatorUtils;
import com.kiilin.modules.pojo.dto.SysDictType;
import com.kiilin.modules.service.SysDictTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 字典类型 前端控制器
 *
 * @author wagk
 * @since 2018-07-24
 */
@RestController
@RequestMapping("/sysDictType")
@Slf4j
public class SysDictTypeController extends AbstractController {

    @Autowired
    SysDictTypeService sysDictTypeService;

    /**
     * 分页
     *
     * @param model
     * @return
     */
    @RequestMapping("list")
    @RequiresPermissions("sysDictType:list")
    public DataTablePager getPage(@RequestBody Map model) {

        // 调用查询
        DataTablePager<SysDictType> page = sysDictTypeService.selectPage(model);
        return page;
    }

    /**
     * 查询单条记录
     *
     * @param id
     * @return
     */
    @RequestMapping("/info")
    @RequiresPermissions("sysDictType:info")
    public ServiceResult info(String id) {

        // 调用查询
        SysDictType info = sysDictTypeService.selectById(id);
        return ServiceResult.success(info);
    }


    /**
     * 修改或保存 字典类型
     *
     * @param sysDictType
     * @return
     */
    @SysLog("修改或保存 字典类型")
    @PostMapping("/save")
    @RequiresPermissions("sysDictType:save")
    public ServiceResult save(SysDictType sysDictType) {
        // 数据校验
        ValidatorUtils.validateEntity(sysDictType);

        // 调用方法
        boolean result = sysDictTypeService.insertOrUpdate(sysDictType);
        
        // 修改字典项，刷新枚举值
        EnumDictUtils.enumDict();
        // 返回结果
        return ServiceResult.success(result);
    }

    /**
     * 删除 字典类型
     *
     * @param id
     * @return
     */
    @SysLog("删除 字典类型")
    @PostMapping("/del")
    @RequiresPermissions("sysDictType:del")
    public ServiceResult del(String id) {
        // 调用方法
        boolean result = sysDictTypeService.deleteById(id);
        // 修改字典项，刷新枚举值
        EnumDictUtils.enumDict();
        // 返回结果
        return ServiceResult.success(result);

    }

}

