package com.kiilin.common.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

/**
 * Shiro权限标签
 * jstl 导入shiro自带标签即可
 * @author wagk
 */
@Component
public class ShiroTag {

	/**
	 * 是否拥有该权限
	 * @param permission
	 * @return
	 */
	public boolean hasPermission(String permission) {
		Subject subject = SecurityUtils.getSubject();
		return subject != null && subject.isPermitted(permission);
	}



}
