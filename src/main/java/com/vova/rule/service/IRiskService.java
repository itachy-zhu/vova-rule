package com.vova.rule.service;

import com.vova.rule.common.request.RiskValidationRequest;
import com.vova.rule.common.vo.RuleValidationVo;
import com.vova.rule.constant.enums.RuleTypeEnum;

import java.util.List;

/**
 * vova risk rule service
 *
 * @author itachy.zhu@gmail.com
 * @since 2020-12-23
 */
@FunctionalInterface
public interface IRiskService<T> {

    /**
     * 获取规则类型
     *
     * @return RuleTypeEnum
     */
    default RuleTypeEnum getRuleType() {return null;}

    /**
     * 实际风控校验
     *
     * @param request 风控校验请求
     * @return 校验结果
     */
    List<RuleValidationVo<T>> doRiskValidation(RiskValidationRequest request);
}
