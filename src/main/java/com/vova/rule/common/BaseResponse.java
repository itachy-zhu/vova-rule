package com.vova.rule.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.UUID;

/**
 * base response
 *
 * @author itachy.zhu@gmail.com
 * @since 2020-12-23
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 0L;

    /**
     * 请求ID
     */
    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("code")
    private int code;

    @JsonProperty("msg")
    private String msg;

    @JsonInclude
    private T data;

    public BaseResponse(String requestId) {
        this.requestId = requestId;
    }

    public BaseResponse(RespCode rc) {
        this.code = rc.getCode();
        this.msg = rc.getMessage();
    }

    public BaseResponse(String requestId, RespCode rc) {
        this.requestId = requestId;
        this.code = rc.getCode();
        this.msg = rc.getMessage();
    }

    public BaseResponse(String requestId, RespCode rc, T data) {
        this.requestId = requestId;
        this.code = rc.getCode();
        this.msg = rc.getMessage();
        this.data = data;
    }

    public BaseResponse(RespCode rc, T data) {
        this.code = rc.getCode();
        this.msg = rc.getMessage();
        this.data = data;
    }

    public static <T> BaseResponse<T> of(BaseResponse<?> response) {
        return new BaseResponse<>(RespCode.of(response.getCode(), response.getMsg()));
    }

    public static <T> BaseResponse<T> init() {
        return new BaseResponse<>(RespCode.INIT);
    }

    public static <T> BaseResponse<T> init(String requestId) {
        return new BaseResponse<>(requestId, RespCode.INIT);
    }

    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>(RespCode.SUCCESS);
    }

    public static <T> BaseResponse<T> success(RespCode rc) {
        return new BaseResponse<>(rc);
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(RespCode.SUCCESS, data);
    }

    public static <T> BaseResponse<T> success(String requestId, T data) {

        if (ObjectUtils.isEmpty(requestId)) {
            requestId = UUID.randomUUID().toString();
        }
        return new BaseResponse<>(requestId, RespCode.SUCCESS, data);
    }

    public static <T> BaseResponse<T> success(RespCode rc, T data) {
        return new BaseResponse<>(rc, data);
    }

    public static <T> BaseResponse<T> fail(RespCode rc) {
        return new BaseResponse<>(rc);
    }

    public static <T> BaseResponse<T> fail(String requestId, RespCode rc) {
        return new BaseResponse<>(requestId, rc);
    }

    public static <T> BaseResponse<T> fail(RespCode rc, T data) {
        return new BaseResponse<>(rc, data);
    }

    public static <T> BaseResponse<T> fail(String requestId, RespCode rc, T data) {
        return new BaseResponse<>(requestId, rc, data);
    }

    public void setResult(RespCode rc) {
        this.setCode(rc.getCode());
        this.setMsg(rc.getMessage());
    }

    public void setResult(RespCode rc, T data) {
        this.code = rc.getCode();
        this.msg = rc.getMessage();
        this.data = data;
    }

    /**
     * 接口调用是否成功（未出现异常返回true， 否则 false）
     */
    @JsonIgnore
    public boolean isSuccess() {
        return RespCode.SUCCESS.getCode() == code;
    }

    public void ifSuccess(@NonNull IRespConsumer<? super T> consumer) {
        if (isSuccess()) {
            consumer.consume(getData());
        }
    }

    public <R> R ifSuccess(@NonNull IRespFunction<? super T, R> handler) {
        if (isSuccess()) {
            return handler.handle(getData());
        }
        return null;
    }
}
