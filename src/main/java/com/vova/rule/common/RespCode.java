package com.vova.rule.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * response code
 *
 * @author itachy.zhu@gmail.com
 * @since 2020-12-23
 */

public class RespCode implements Serializable {
    private static final long serialVersionUID = 0L;

    @JsonProperty("code")
    private int code;

    @JsonProperty("message")
    private String message;

    public RespCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static RespCode of(HttpStatus status) {
        return new RespCode(status.value(), status.getReasonPhrase());
    }

    public static RespCode of(int code, String message) {
        return new RespCode(code, message);
    }

    public static RespCode illegalArgument(String message) {
        return new RespCode(RespCode.ILLEGAL_ARGUMENT.getCode(), String.format("%s: %s", RespCode.ILLEGAL_ARGUMENT.getMessage(), message));
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * common code
     */
    public static final RespCode INIT = new RespCode(100, "INIT");
    public static final RespCode SUCCESS = new RespCode(200, "OK");

    public static final RespCode ALREADY_SUCCESS = new RespCode(200, "ALREADY_SUCCESS");

    public static final RespCode SERVER_ERROR = new RespCode(110100, "服务异常");
    public static final RespCode ILLEGAL_ARGUMENT = new RespCode(110101, "请求参数非法");
}
