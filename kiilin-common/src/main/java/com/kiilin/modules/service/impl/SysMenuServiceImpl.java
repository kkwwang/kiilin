package com.kiilin.modules.service.impl;

import com.kiilin.common.abstracts.service.impl.AbstractServiceImpl;
import com.kiilin.common.redis.RedisKeys;
import com.kiilin.common.shiro.ShiroConfigEntity;
import com.kiilin.modules.dao.SysMenuDao;
import com.kiilin.modules.pojo.dto.SysMenu;
import com.kiilin.modules.pojo.entity.SysMenuEntity;
import com.kiilin.modules.pojo.enums.dict.MenuTypeEnum;
import com.kiilin.modules.pojo.enums.dict.SysCodeEnum;
import com.kiilin.modules.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单表 服务实现类
 *
 * @author wangkai
 * @since 2018-07-24
 */
@Service
@Slf4j
public class SysMenuServiceImpl extends AbstractServiceImpl<SysMenuDao, SysMenu> implements SysMenuService {

    @Value("${sys.code}")
    String sysCode;


    @Autowired
    ShiroConfigEntity entity;

    @Override
    public List<SysMenu> getMenuByUser(String userId, String[] types, SysCodeEnum sysCode) {
        return baseMapper.getMenuByUser(userId, types,sysCode);
    }

    @Override
    @Cacheable(value = RedisKeys.CACHE_2_HOU)
    public List<SysMenuEntity> selectTree(SysCodeEnum sysCode) {
        List<SysMenuEntity> list = baseMapper.selectTree(sysCode.getValue(), sysCode);
        return list;
    }

    @Override
    @Cacheable(value = RedisKeys.CACHE_2_HOU)
    public List<SysMenuEntity> selectTreeNoneAction(SysCodeEnum sysCode) {
        List<SysMenuEntity> list = baseMapper.selectTreeNoneAction(sysCode.getValue(), sysCode);
        return list;
    }

    @Override
    public Map<String, String> getShiroMenuConfig() {

        Map<String, String> filterMap = new LinkedHashMap<>();

        for (String url : entity.getOther().getAnon()) {
            filterMap.put(url, "anon");
        }

        // 查询其他需要权限才能访问的url
        List<SysMenu> sysMenus = selectList(null);
        for (SysMenu menu : sysMenus) {
            if (null != menu) {
                // 如果为菜单类型 且权限不为空
                if (
                        StringUtils.equalsIgnoreCase(MenuTypeEnum.MENU.getValue(), menu.getMenuType().getValue())
                                // 判断是否需要权限
                                && StringUtils.isNotBlank(menu.getPermission())
                                && StringUtils.isNotBlank(menu.getMenuHref())
                                // 判断系统
                                && StringUtils.equalsIgnoreCase(sysCode, menu.getSysCode().getValue())
                        ) {
                    filterMap.put("/" + menu.getMenuHref(), "perms[" + menu.getPermission() + "]");
                }
            }
        }

        for (String url : entity.getOther().getAuthc()) {
            filterMap.put(url, "authc");
        }

        // 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterMap.put(entity.getLogoutUrl(), "logout");

        return filterMap;
    }


}
