package com.vova.rule.service.impl;

import com.google.common.collect.Lists;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import com.vova.rule.common.configuration.BlockListStore;
import com.vova.rule.common.request.RiskValidationRequest;
import com.vova.rule.common.vo.BlockReasonVo;
import com.vova.rule.common.vo.RuleValidationVo;
import com.vova.rule.constant.enums.RuleTypeEnum;
import com.vova.rule.service.IUserAndAmountRiskService;
import com.vova.rule.service.basic.BaseRuleService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class QlExpressRiskServiceImpl implements IUserAndAmountRiskService {

    @Resource
    private ExpressRunner expressRunner;

    @Resource
    private BaseRuleService baseRuleService;

    @Resource
    private BlockListStore<BigDecimal> blockListStore;

    @Override
    public RuleTypeEnum getRuleType() {
        return RuleTypeEnum.QL_EXPRESS;
    }

    @Override
    public List<RuleValidationVo<BigDecimal>> doRiskValidation(RiskValidationRequest request) {
        try {
            //记录风控信息
            baseRuleService.recordRiskHistory(request);
            BigDecimal amount = baseRuleService.getUserRiskHistoryCache().getOrDefault(request.getUserId(), BigDecimal.ZERO);

            DefaultContext<String, Object> context = new DefaultContext<>();
            context.put("amount", amount);
            String express = "amount > 100";
            Boolean blocked = (Boolean) expressRunner.execute(express, context, null, true, false);

            if (blocked) {
                blockListStore.setBlockReason(request.getUserId(), BlockReasonVo.<BigDecimal>builder().userId(request.getUserId()).key(amount).type(getRuleType().getType()).rule("more than 100").reason("total amount > 100").build());
                return Lists.newArrayList(RuleValidationVo.<BigDecimal>builder().pass(false).key(amount).type(getRuleType().getType()).rule("more than 100").reason("total amount > 100").build());
            } else {
                return Lists.newArrayList(RuleValidationVo.<BigDecimal>builder().pass(true).key(amount).type(getRuleType().getType()).rule("more than 100").build());
            }
        } catch (Exception e) {
            log.error("ql-risk-validation error, error msg: ", e);
            return Lists.newArrayList();
        }
    }
}