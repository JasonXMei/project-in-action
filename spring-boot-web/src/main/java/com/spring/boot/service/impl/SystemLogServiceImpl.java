package com.spring.boot.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.boot.dto.PageReq;
import com.spring.boot.dto.PageResp;
import com.spring.boot.dto.SystemLogResp;
import com.spring.boot.entity.Credential;
import com.spring.boot.entity.SystemLog;
import com.spring.boot.mapper.SystemLogMapper;
import com.spring.boot.service.SystemLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Jason
 * @since 2023-12-27
 */
@Service
public class SystemLogServiceImpl extends ServiceImpl<SystemLogMapper, SystemLog> implements SystemLogService {

    @Autowired
    private SystemLogMapper systemLogMapper;

    @Override
    public PageResp<SystemLogResp> getList(PageReq req) {
        Page<SystemLog> page = new Page<>(req.getCurrentPage(), req.getPageSize());
        IPage<SystemLog> pageResult = systemLogMapper.selectPage(page, Wrappers.emptyWrapper());

        List<SystemLogResp> systemLogResps = null;
        if (CollectionUtil.isNotEmpty(pageResult.getRecords())) {
            systemLogResps = pageResult.getRecords().stream()
                    .map(systemLog -> SystemLogResp.builder()
                            .id(systemLog.getId())
                            .logTime(systemLog.getCreatedTime())
                            .uri(systemLog.getUri())
                            .spendTime(systemLog.getSpendTime())
                            .method(systemLog.getMethod())
                            .header(JSONUtil.parse(systemLog.getHeader()))
                            .parameter(JSONUtil.parse(systemLog.getParameter()))
                            .response(JSONUtil.parse(systemLog.getResponse()))
                            .exception(systemLog.getException())
                            .build())
                    .collect(Collectors.toList());
        } else {
            systemLogResps = Collections.emptyList();
        }

        return PageResp.<SystemLogResp>builder()
                .list(systemLogResps)
                .currentPage(pageResult.getCurrent())
                .pageNumber(pageResult.getPages())
                .pageSize(pageResult.getSize())
                .total(pageResult.getTotal())
                .build();
    }
}
