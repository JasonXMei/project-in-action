package com.spring.boot.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.spring.boot.enums.ArgorithmEnum;

/**
 * @Author Jason
 * @Date 2023-04-26
 */
@Data
public class CredentialCreateReq {

    @NotBlank
    @ApiModelProperty(required = true, value = "appId")
    private String appId;

    @NotBlank
    @ApiModelProperty(required = true, value = "appSecret")
    private String appSecret;

    @NotBlank
    @ApiModelProperty(required = true, value = "盐值")
    private String signer;

    @NotNull
    @ApiModelProperty(required = true, value = "加密算法")
    private ArgorithmEnum algorithm;

    @NotNull
    @ApiModelProperty(required = true, value = "过期时间（秒）")
    private Long expiredAt;

}
