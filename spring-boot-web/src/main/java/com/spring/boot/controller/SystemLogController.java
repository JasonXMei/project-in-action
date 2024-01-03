package com.spring.boot.controller;

import com.spring.boot.dto.BaseResp;
import com.spring.boot.dto.PageReq;
import com.spring.boot.dto.PageResp;
import com.spring.boot.dto.SystemLogResp;
import com.spring.boot.service.SystemLogService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jason
 * @since 2023-12-27
 */
@Api(tags = "SystemLog APIs")
@RestController
@RequestMapping("/systemLog")
public class SystemLogController {

    @Autowired
    private SystemLogService systemLogService;

    @Operation(summary = "List")
    @GetMapping("/list")
    public BaseResp<PageResp<SystemLogResp>> list(@Validated PageReq req) {
        PageResp<SystemLogResp> list = systemLogService.getList(req);
        return BaseResp.success(list);
    }

}
