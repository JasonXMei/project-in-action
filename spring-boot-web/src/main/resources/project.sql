CREATE DATABASE `spring-boot-web` DEFAULT CHARACTER SET utf8mb4;

DROP TABLE IF EXISTS `credential`;
CREATE TABLE `credential`(
    `id` BIGINT (20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `created_time` DATETIME(0) NULL DEFAULT NULL COMMENT '创建时间',
    `last_modified_time` DATETIME(0) NULL DEFAULT NULL COMMENT '最后修改时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '删除标识',
    `version` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '版本号',
    `app_id` VARCHAR(50) NOT NULL COMMENT 'appId',
    `app_secret` VARCHAR(50) NOT NULL COMMENT 'appSecret',
    `signer` VARCHAR(50) NOT NULL COMMENT 'token 盐值',
    `algorithm` VARCHAR(10) NOT NULL COMMENT 'token 生成算法',
    `expired_at`  BIGINT(20) NOT NULL COMMENT 'token 过期时间（秒）',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_app_id` (`app_id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS `system_log`;
CREATE TABLE `system_log`(
     `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
     `created_time` DATETIME(0) NULL DEFAULT NULL COMMENT '创建时间',
     `last_modified_time` DATETIME(0) NULL DEFAULT NULL COMMENT '最后修改时间',
     `deleted` TINYINT DEFAULT 0 COMMENT '删除标识',
     `version` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '版本号',
     `spend_time` BIGINT(20) NOT NULL COMMENT '执行时间(毫秒)',
     `uri` VARCHAR(100) NOT NULL COMMENT '请求地址',
     `method` VARCHAR(10) NOT NULL COMMENT '请求方法',
     `header` LONGTEXT DEFAULT NULL COMMENT '请求头',
     `parameter` LONGTEXT DEFAULT NULL COMMENT '请求参数',
     `response` LONGTEXT DEFAULT NULL COMMENT '响应',
     `exception` LONGTEXT DEFAULT NULL COMMENT '异常原因',
     PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4;