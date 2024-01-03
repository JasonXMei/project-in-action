package com.spring.boot.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @Author Jason
 * @Date 2022/06/21
 */
@Data
@Builder
public class TokenCreateResp {

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("过期时间(秒)")
    private Long expiredAt;

    @ApiModelProperty("签发时间(秒)")
    private Long issuedAt;

}
