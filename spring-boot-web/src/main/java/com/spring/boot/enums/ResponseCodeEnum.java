package com.spring.boot.enums;

/**
 * @Author Jason
 * @Date 2023-04-25
 */
public enum ResponseCodeEnum {

    /**
     * Success
     */
    OK(200, "Success"),

    /**
     * Bad Request
     */
    BAD_REQUEST(400, "Bad Request"),

    /**
     * Unauthorized
     */
    UNAUTHORIZED(401, "Unauthorized"),

    /**
     * Business Exception
     */
    BUSINESS_EXCEPTION(403, "Business Exception"),

    /**
     * Method not support
     */
    HTTP_BAD_METHOD(405, "Method Not allowed"),

    /**
     * Internal Server Error
     */
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private Integer code;
    private String message;

    ResponseCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
