package com.spring.boot.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author Jason
 * @Date 2022-06-21
 */
@Data
@Builder
public class PageResp<T> {

    @ApiModelProperty("当前页")
    private Long currentPage;

    @ApiModelProperty("每页大小")
    private Long pageSize;

    @ApiModelProperty("页数")
    private Long pageNumber;

    @ApiModelProperty("总数据条数")
    private Long total;

    @ApiModelProperty("数据 list")
    private List<T> list;

}
