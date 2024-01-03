package com.mybatis.plus.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatis.plus.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jason
 * @since 2023-12-22
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    User selectByUserId(Long userId);

    IPage<User> customPage(Page<User> userPage, @Param("user") User user);
}
