package com.spring.boot.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @Author Jason
 * @Date 2023-04-26
 */
@Data
@Builder
public class CredentialCreateResp {

    @ApiModelProperty("id")
    private Long id;

}
