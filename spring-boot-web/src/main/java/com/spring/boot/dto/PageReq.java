package com.spring.boot.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author Jason
 * @Date 2022-06-21
 */
@Data
public class PageReq {

    @NotNull
    private Long currentPage;

    @NotNull
    private Long pageSize;

}
