package com.kiilin.modules.controller;

import com.kiilin.common.abstracts.controller.AbstractController;
import com.kiilin.common.datatables.DataTablePager;
import com.kiilin.modules.pojo.dto.SysDict;
import com.kiilin.modules.service.SysDictService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * datatables 分页demo
 *
 * @author wagk
 */
@RestController
@RequestMapping("demos")
@Slf4j
public class DataTablesDemoController extends AbstractController {


    @Autowired
    SysDictService dictService;

    /**
     * 分页demo
     *
     * @param model
     * @return
     */
    @RequestMapping("test")
    @RequiresPermissions("system:manager")
    public DataTablePager test(@RequestBody Map model) {
        DataTablePager<SysDict> pager = dictService.selectPageBySql(model);
        return pager;
    }
}
