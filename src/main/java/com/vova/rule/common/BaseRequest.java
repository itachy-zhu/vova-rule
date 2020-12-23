package com.vova.rule.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * base request
 *
 * @author itachy.zhu@gmail.com
 * @since 2020-12-23
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseRequest implements Serializable {
    private static final long serialVersionUID = 0L;

    /**
     * 请求ID
     */
    @JsonProperty("request_id")
    private String requestId = UUID.randomUUID().toString();
}
