package com.vova.rule.common.request;

import com.vova.rule.common.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * rule validation request
 *
 * @author itachy.zhu@gmail.com
 * @since 2020-12-23
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskValidationRequest extends BaseRequest {

    private String ruleType;

    private Integer userId;

    private BigDecimal amount;
}
