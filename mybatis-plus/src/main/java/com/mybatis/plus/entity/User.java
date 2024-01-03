package com.mybatis.plus.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.mybatis.plus.enums.AgeEnum;
import com.mybatis.plus.enums.GenderEnum;
import lombok.*;

/**
 * <p>
 *
 * </p>
 *
 * @author Jason
 * @since 2023-12-22
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "user", autoResultMap = true)
public class User implements Serializable {

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
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 年龄
     */
    @TableField("age")
    private AgeEnum age;

    /**
     * 性别
     */
    @TableField("gender")
    private GenderEnum gender;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    @TableField(value = "address", typeHandler = JacksonTypeHandler.class)
    private Address address;

}
