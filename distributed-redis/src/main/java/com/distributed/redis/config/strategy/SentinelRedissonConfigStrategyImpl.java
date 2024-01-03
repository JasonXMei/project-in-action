package com.distributed.redis.config.strategy;

import com.distributed.redis.config.RedissonProperties;
import com.distributed.redis.constant.GlobalConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;

/**
 * @desc 哨兵集群方式Redis连接配置
 */
@Slf4j
public class SentinelRedissonConfigStrategyImpl implements RedissonConfigStrategy {

    @Override
    public Config createRedissonConfig(RedissonProperties redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String password = redissonProperties.getPassword();
            int database = redissonProperties.getDatabase();
            String[] addrTokens = address.split(",");
            String sentinelAliasName = addrTokens[0];
            /**设置redis配置文件sentinel.conf配置的sentinel别名*/
            config.useSentinelServers()
                    .setMasterName(sentinelAliasName);
            config.useSentinelServers().setDatabase(database);
            if (StringUtils.isNotBlank(password)) {
                config.useSentinelServers().setPassword(password);
            }
            /**设置sentinel节点的服务IP和端口*/
            for (int i = 1; i < addrTokens.length; i++) {
                config.useSentinelServers().addSentinelAddress(GlobalConstant.REDIS_CONNECTION_PREFIX.getConstantValue() + addrTokens[i]);
            }
            log.info("sentinel Redisson init, redisAddress: {}", address);
        } catch (Exception e) {
            log.error("sentinel Redisson init error", e);
        }
        return config;
    }
}
