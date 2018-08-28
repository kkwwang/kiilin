package com.kiilin.common.redis;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * redis 托管session的配置
 *
 * @author wagk
 */
@Configuration
@EnableRedisHttpSession(redisNamespace = "spring:session:${sys.name}:${sys.code}")
public class RedisSessionConfig {
}
