package com.vova.rule.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * block-reason vo
 *
 * @author itachy.zhu@gmail.com
 * @since 2020-12-23
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockReasonVo<T> implements Serializable {

    private static final long serialVersionUID = 0L;

    private T key;

    private Integer userId;

    private String type;

    private String rule;

    private String reason;
}
