CREATE DATABASE `mybatis-plus-db` CHARACTER SET utf8mb4;

DROP TABLE IF EXISTS user;
CREATE TABLE user(
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `created_time` DATETIME(0) NULL DEFAULT NULL COMMENT '创建时间',
    `last_modified_time` DATETIME(0) NULL DEFAULT NULL COMMENT '最后修改时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '删除标识',
    `version` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '版本号',
	`name` VARCHAR(30) DEFAULT NULL COMMENT '姓名',
	`age` VARCHAR(10) DEFAULT 'ONE' COMMENT '年龄',
	`gender` INT(11) DEFAULT 0 COMMENT '性别',
	`email` VARCHAR(50) DEFAULT NULL COMMENT '邮箱',
    `address` LONGTEXT DEFAULT NULL COMMENT '地址',
	PRIMARY KEY (id),
	INDEX `idx_name` (`name`),
	UNIQUE INDEX `uk_email` (`email`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4;