package com.spring.boot.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author Jason
 * @Date 2022-06-21
 */
@Data
@ApiModel("创建 token")
public class TokenCreateReq {

    @ApiModelProperty("appId")
    @NotBlank
    private String appId;

    @ApiModelProperty("appSecret")
    @NotBlank
    private String appSecret;

}
