/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.kiilin.job.controller;

import com.kiilin.common.abstracts.result.ServiceResult;
import com.kiilin.common.datatables.DataTablePager;
import com.kiilin.job.entity.ScheduleJobLogEntity;
import com.kiilin.job.service.ScheduleJobLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 定时任务日志
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.2.0 2016-11-28
 */
@RestController
@RequestMapping("/sys/scheduleLog")
public class ScheduleJobLogController {
    @Autowired
    private ScheduleJobLogService scheduleJobLogService;

    /**
     * 定时任务日志列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:schedule:log")
    public DataTablePager list(@RequestBody Map params) {
        DataTablePager<ScheduleJobLogEntity> pager = scheduleJobLogService.selectPage(params);
        return pager;
    }

    /**
     * 定时任务日志信息
     */
    @RequestMapping("/info/{logId}")
    public ServiceResult info(@PathVariable("logId") String logId) {
        ScheduleJobLogEntity log = scheduleJobLogService.selectById(logId);

        return ServiceResult.success().setData(log);
    }
}
