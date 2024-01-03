package com.spring.boot.service;

import com.spring.boot.dto.PageReq;
import com.spring.boot.dto.PageResp;
import com.spring.boot.dto.SystemLogResp;
import com.spring.boot.entity.SystemLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jason
 * @since 2023-12-27
 */
public interface SystemLogService extends IService<SystemLog> {

    PageResp<SystemLogResp> getList(PageReq req);

}
