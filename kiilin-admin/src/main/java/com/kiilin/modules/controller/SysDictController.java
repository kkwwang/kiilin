package com.kiilin.modules.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kiilin.common.abstracts.controller.AbstractController;
import com.kiilin.common.abstracts.result.ServiceResult;
import com.kiilin.common.annotation.SysLog;
import com.kiilin.common.datatables.DataTablePager;
import com.kiilin.common.util.EnumDictUtils;
import com.kiilin.common.validator.ValidatorUtils;
import com.kiilin.modules.pojo.dto.SysDict;
import com.kiilin.modules.service.SysDictService;
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
 * 数据字典 前端控制器
 *
 * @author wagk
 * @since 2018-07-24
 */
@RestController
@RequestMapping("/sysDict")
@Slf4j
public class SysDictController extends AbstractController {

    @Autowired
    SysDictService dictService;


    /**
     * 分页
     *
     * @param model
     * @return
     */
    @RequestMapping("list")
    @RequiresPermissions("sysDict:list")
    public DataTablePager getPage(@RequestBody Map model) {

        // 调用查询
        DataTablePager<SysDict> page = dictService.selectPage(model);
        return page;
    }

    /**
     * 查询单条记录
     *
     * @param id
     * @return
     */
    @RequestMapping("/info")
    @RequiresPermissions("sysDict:info")
    public ServiceResult info(String id) {

        // 调用查询
        SysDict info = dictService.selectById(id);
        return ServiceResult.success(info);
    }


    /**
     * 修改或新增 数据字典
     *
     * @param sysDict
     * @return
     */
    @SysLog("修改或新增 数据字典")
    @PostMapping("/save")
    @RequiresPermissions("sysDict:save")
    public ServiceResult save(SysDict sysDict) {
        // 数据校验
        ValidatorUtils.validateEntity(sysDict);

        // 调用方法
        boolean result = dictService.insertOrUpdate(sysDict);
        // 修改字典项，刷新枚举值
        EnumDictUtils.enumDict();
        // 返回结果
        return ServiceResult.success(result);
    }

    /**
     * 删除 数据字典
     *
     * @param id
     * @return
     */
    @SysLog("删除 数据字典")
    @PostMapping("/del")
    @RequiresPermissions("sysDict:del")
    public ServiceResult del(String id) {
        // 调用方法
        boolean result = dictService.deleteById(id);
        // 修改字典项，刷新枚举值
        EnumDictUtils.enumDict();
        // 返回结果
        return ServiceResult.success(result);

    }

    /**
     * 根据菜单类型查询菜单
     * @param type
     * @return
     */
    @RequestMapping("getDictByType")
    public ServiceResult getDictByType(String type){

        SysDict dict = new SysDict();
        dict.setType(type);
        List<SysDict> list = dictService.selectList(new EntityWrapper<>(dict).orderBy("sort"));

        return ServiceResult.success(list);
    }
}

