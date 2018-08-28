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

package com.kiilin.job.service;

import com.kiilin.common.abstracts.service.AbstractService;
import com.kiilin.job.dao.ScheduleJobDao;
import com.kiilin.job.entity.ScheduleJobEntity;

/**
 * 定时任务
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.2.0 2016-11-28
 */
public interface ScheduleJobService extends AbstractService<ScheduleJobDao, ScheduleJobEntity> {
//	/**
//	 * 分页查询
//	 *
//	 * @param params
//	 * @return
//	 */
//	DataTablePager<ScheduleJobEntity> queryPage(Map<String, Object> params);

	/**
	 * 保存定时任务
	 * @param scheduleJob
	 */
	void save(ScheduleJobEntity scheduleJob);

	/**
	 * 更新定时任务
	 * @param scheduleJob
	 */
	void update(ScheduleJobEntity scheduleJob);

	/**
	 * 批量删除定时任务
	 * @param jobIds
	 */
	void deleteBatch(String[] jobIds);

	/**
	 * 批量更新定时任务状态
	 * @param jobIds
	 * @param status
	 * @return
	 */
	int updateBatch(String[] jobIds, int status);

	/**
	 * 立即执行
	 * @param jobIds
	 */
	void run(String[] jobIds);

	/**
	 * 暂停运行
	 * @param jobIds
	 */
	void pause(String[] jobIds);

	/**
	 * 恢复运行
	 * @param jobIds
	 */
	void resume(String[] jobIds);
}
