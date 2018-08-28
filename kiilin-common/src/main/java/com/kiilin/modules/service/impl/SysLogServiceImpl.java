
package com.kiilin.modules.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kiilin.modules.dao.SysLogDao;
import com.kiilin.modules.pojo.dto.SysLogEntity;
import com.kiilin.modules.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 系统日志
 *
 * @author wagk
 */
@Service
@Slf4j
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {

}
