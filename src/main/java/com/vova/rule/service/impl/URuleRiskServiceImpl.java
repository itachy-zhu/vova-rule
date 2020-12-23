package com.vova.rule.service.impl;

import com.google.common.collect.Lists;
import com.vova.rule.common.request.RiskValidationRequest;
import com.vova.rule.common.vo.RuleValidationVo;
import com.vova.rule.constant.enums.RuleTypeEnum;
import com.vova.rule.service.IUserAndAmountRiskService;
import com.vova.rule.service.basic.BaseRuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * easy-rule service impl
 *
 * @author itachy.zhu@gmail.com
 * @since 2020-12-23
 */
@Service
public class URuleRiskServiceImpl implements IUserAndAmountRiskService {

    @Resource
    private BaseRuleService baseRuleService;

    @Override
    public RuleTypeEnum getRuleType() {
        return RuleTypeEnum.URULE;
    }

    @Override
    public List<RuleValidationVo<BigDecimal>> doRiskValidation(RiskValidationRequest request) {
        //记录风控信息
        baseRuleService.recordRiskHistory(request);
        BigDecimal amount = baseRuleService.getUserRiskHistoryCache().getOrDefault(request.getUserId(), BigDecimal.ZERO);

        RuleValidationVo<BigDecimal> ruleValidationVo = new RuleValidationVo<>();
        ruleValidationVo.setType(getRuleType().getType());

        return Lists.newArrayList(ruleValidationVo);
    }
}