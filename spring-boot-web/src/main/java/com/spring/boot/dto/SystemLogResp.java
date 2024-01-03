package com.spring.boot.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Jason
 * @since 2023-12-27
 */
@Data
@Builder
public class SystemLogResp {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("日志时间")
    private LocalDateTime logTime;

    @ApiModelProperty("执行时间")
    private Long spendTime;

    @ApiModelProperty("请求 uri")
    private String uri;

    @ApiModelProperty("http method")
    private String method;

    @ApiModelProperty("请求 header")
    private Object header;

    @ApiModelProperty("请求参数")
    private Object parameter;

    @ApiModelProperty("响应")
    private Object response;

    @ApiModelProperty("异常信息")
    private Object exception;

}
