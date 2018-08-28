package com.kiilin.common.redis;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * redis 托管session的配置  session 已交由shiro管理
 *
 * @author wagk
 */
@Configuration
@EnableRedisHttpSession
public class RedisSessionConfig {
}
