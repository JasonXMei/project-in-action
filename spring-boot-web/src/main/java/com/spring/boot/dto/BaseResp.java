package com.spring.boot.dto;

import com.spring.boot.enums.ResponseCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

/**
 * @Author Jason
 * @Date 2022-06-21
 */
@Data
@Builder
@ApiModel("API 通用响应")
public class BaseResp<T> {

    @ApiModelProperty("响应代码")
    private Integer code;

    @ApiModelProperty("错误描述")
    private String msg;

    @ApiModelProperty("业务数据")
    private T body;

    public static <T> BaseResp<T> success() {
        return success(null);
    }

    public static <T> BaseResp<T> success(T body) {
        return BaseResp.<T>builder()
                .code(ResponseCodeEnum.OK.getCode())
                .msg(ResponseCodeEnum.OK.getMessage())
                .body(body)
                .build();
    }

    public static <T> BaseResp<T> fail(ResponseCodeEnum responseCode) {
        return BaseResp.<T>builder()
                .code(responseCode.getCode())
                .msg(responseCode.getMessage())
                .build();
    }

    public static <T> BaseResp<T> fail(ResponseCodeEnum responseCode, String extraMsg) {
        if (Objects.isNull(extraMsg)) {
            return fail(responseCode);
        } else {
            return BaseResp.<T>builder()
                    .code(responseCode.getCode())
                    .msg(String.format("%s | %s", responseCode.getMessage(), extraMsg))
                    .build();
        }
    }

}
