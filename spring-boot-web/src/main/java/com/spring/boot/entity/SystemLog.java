package com.spring.boot.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Jason
 * @since 2023-12-27
 */
@Getter
@Setter
@TableName("system_log")
public class SystemLog implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 执行时间(毫秒)
     */
    @TableField("spend_time")
    private Long spendTime;

    /**
     * 请求地址
     */
    @TableField("uri")
    private String uri;

    /**
     * 请求方法
     */
    @TableField("method")
    private String method;

    /**
     * 请求头
     */
    @TableField("header")
    private String header;

    /**
     * 请求参数
     */
    @TableField("parameter")
    private String parameter;

    /**
     * 响应
     */
    @TableField("response")
    private String response;

    /**
     * 异常原因
     */
    @TableField("exception")
    private String exception;


}
