package com.distributed.redis.config;

import com.distributed.redis.manager.RedissonLock;
import com.distributed.redis.manager.RedissonManager;
import com.distributed.redis.manager.RedissonRateLimit;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @desc Redisson自动化配置
 */
@Configuration
@ConditionalOnClass(Redisson.class)
@EnableConfigurationProperties(RedissonProperties.class)
@Slf4j
public class RedissonConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @Order(value = 1)
    public RedissonManager redissonManager(RedissonProperties redissonProperties) {
        RedissonManager redissonManager = new RedissonManager(redissonProperties);
        log.info("[RedissonManager] is assembled, current connection method: [{}], connection address: [{}]", redissonProperties.getType(), redissonProperties.getAddress());
        return redissonManager;
    }

    @Bean
    @ConditionalOnMissingBean
    @Order(value = 2)
    public RedissonLock redissonLock(RedissonManager redissonManager) {
        RedissonLock redissonLock = new RedissonLock();
        redissonLock.setRedissonManager(redissonManager);
        log.info("[RedissonLock] is assembled");
        return redissonLock;
    }

    @Bean
    @ConditionalOnMissingBean
    @Order(value = 3)
    public RedissonRateLimit redissonRateLimit(RedissonManager redissonManager) {
        RedissonRateLimit redissonRateLimit = new RedissonRateLimit();
        redissonRateLimit.setRedissonManager(redissonManager);
        log.info("[RedissonRateLimit] is assembled");
        return redissonRateLimit;
    }

}

