package com.spring.boot.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.spring.boot.enums.ArgorithmEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author Jason
 * @since 2022-06-14
 */
@Data
public class Credential {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /**
     * 最后修改时间
     */
    @TableField(value = "last_modified_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime lastModifiedTime;

    /**
     * 删除标识
     */
    @TableField("deleted")
    @TableLogic
    private Integer deleted;

    /**
     * 版本号
     */
    @TableField("version")
    @Version
    private Long version;

    /**
     * appId
     */
    @TableField("app_id")
    private String appId;

    /**
     * appSecret
     */
    @TableField("app_secret")
    private String appSecret;

    /**
     * token 盐值
     */
    @TableField("signer")
    private String signer;

    /**
     * token 生成算法
     */
    @TableField("algorithm")
    private ArgorithmEnum algorithm;

    /**
     * token 过期时间（秒）
     */
    @TableField("expired_at")
    private Long expiredAt;

}
