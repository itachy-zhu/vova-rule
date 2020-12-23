package com.vova.rule.service.impl;

import com.google.common.collect.Lists;
import com.vova.rule.common.configuration.BlockListStore;
import com.vova.rule.common.request.RiskValidationRequest;
import com.vova.rule.common.vo.BlockReasonVo;
import com.vova.rule.common.vo.RuleValidationVo;
import com.vova.rule.constant.enums.RuleTypeEnum;
import com.vova.rule.constant.rules.UserAndAmountRule;
import com.vova.rule.service.IUserAndAmountRiskService;
import com.vova.rule.service.basic.BaseRuleService;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
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
public class EasyRuleRiskServiceImpl implements IUserAndAmountRiskService {

    @Resource
    private RulesEngine easyRuleEngine;

    @Resource
    private BaseRuleService baseRuleService;

    @Resource
    private BlockListStore<BigDecimal> blockListStore;

    @Override
    public RuleTypeEnum getRuleType() {
        return RuleTypeEnum.EASY_RULE;
    }

    @Override
    public List<RuleValidationVo<BigDecimal>> doRiskValidation(RiskValidationRequest request) {

        //记录用户操作信息
        baseRuleService.recordRiskHistory(request);
        BigDecimal amount = baseRuleService.getUserRiskHistoryCache().getOrDefault(request.getUserId(), BigDecimal.ZERO);
        // define facts
        Facts facts = new Facts();
        facts.put("moreThan", amount.longValue() > 100L);

        // define rules
        Rules rules = new Rules();
        rules.register(UserAndAmountRule.builder().userId(request.getUserId()).amount(amount).blockListStore(blockListStore).build());

        // fire rules on known facts
        easyRuleEngine.fire(rules, facts);
        if (blockListStore.isBlocked(request.getUserId())) {
            BlockReasonVo<BigDecimal> blockReason = blockListStore.getBlockReason(request.getUserId());
            return Lists.newArrayList(RuleValidationVo.<BigDecimal>builder().pass(false).key(blockReason.getKey()).type(blockReason.getType()).rule(blockReason.getRule()).reason(blockReason.getReason()).build());
        }

        return Lists.newArrayList(RuleValidationVo.<BigDecimal>builder().pass(true).key(amount).type(RuleTypeEnum.EASY_RULE.getType()).rule("more than 100").build());
    }
}