package com.vova.rule.constant.enums;

import lombok.Getter;

/**
 * rule type enum
 *
 * @author itachy.zhu@gmail.com
 * @since 2020-12-23
 */
@Getter
public enum RuleTypeEnum {
    /**
     * 规则类型
     */
    URULE("u-rule"),

    EASY_RULE("easy-rule"),

    QL_EXPRESS("Ql-express");

    private final String type;

    RuleTypeEnum(String type) {
        this.type = type;
    }
}
