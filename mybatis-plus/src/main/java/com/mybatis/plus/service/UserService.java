package com.mybatis.plus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatis.plus.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jason
 * @since 2023-12-22
 */
public interface UserService extends IService<User> {

    User selectByUserId(long userId);

    IPage<User> customPage(Page<User> userPage, User user);

}
