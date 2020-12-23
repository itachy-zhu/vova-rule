package com.vova.rule.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * rule validation vo
 *
 * @author itachy.zhu@gmail.com
 * @since 2020-12-23
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleValidationVo<T> implements Serializable {

    private static final long serialVersionUID = 0L;

    private T key;

    private String rule;

    private String type;

    private boolean pass;

    private String reason;
}