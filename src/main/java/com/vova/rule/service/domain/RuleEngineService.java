package com.vova.rule.service.domain;

import com.google.common.collect.Lists;
import com.vova.rule.common.BaseResponse;
import com.vova.rule.common.RespCode;
import com.vova.rule.common.configuration.BlockListStore;
import com.vova.rule.common.request.RiskValidationRequest;
import com.vova.rule.common.utils.ParameterUtil;
import com.vova.rule.common.vo.BlockReasonVo;
import com.vova.rule.common.vo.RuleValidationVo;
import com.vova.rule.constant.enums.RuleTypeEnum;
import com.vova.rule.service.IUserAndAmountRiskService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * rule-engine service
 *
 * @author itachy.zhu@gmail.com
 * @since 2020-12-23
 */
@Service
public class RuleEngineService implements ApplicationContextAware {

    @Resource
    private BlockListStore<BigDecimal> blockListStore;

    private static final Map<RuleTypeEnum, IUserAndAmountRiskService> RULE_SERVICE_MAP = new HashMap<>(16);

    public BaseResponse<List<RuleValidationVo<BigDecimal>>> validateRisk(RiskValidationRequest request) {

        //鉴参
        if (ParameterUtil.isAnyNull(request, request.getUserId(), request.getAmount())) {
            return BaseResponse.fail(RespCode.illegalArgument("request, userId, amount"));
        }
        //校验是否blocked
        if (blockListStore.isBlocked(request.getUserId())) {
            BlockReasonVo<BigDecimal> blockReason = blockListStore.getBlockReason(request.getUserId());
            return BaseResponse.success(
                Lists.newArrayList(
                    RuleValidationVo.<BigDecimal>builder()
                        .pass(false)
                        .key(blockReason.getKey())
                        .type(blockReason.getType())
                        .rule(blockReason.getRule())
                        .reason(blockReason.getReason())
                        .build()
                )
            );
        }
        //检查服务实例
        if (CollectionUtils.isEmpty(RULE_SERVICE_MAP)) {
            return BaseResponse.fail(RespCode.SERVER_ERROR);
        }

        //调用风控
        List<RuleValidationVo<BigDecimal>> validationResult = new ArrayList<>();
        RULE_SERVICE_MAP.forEach((ruleTypeEnum, ruleService) -> {
            List<RuleValidationVo<BigDecimal>> validationVoList = ruleService.doRiskValidation(request);
            if (!CollectionUtils.isEmpty(validationVoList)) {
                validationResult.addAll(validationVoList);
            }
        });
        return BaseResponse.success(request.getRequestId(), validationResult);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        initPropertiesSet(applicationContext);
    }

    public void initPropertiesSet(ApplicationContext applicationContext) {
        Map<String, IUserAndAmountRiskService> ruleNameAndServiceMap = applicationContext.getBeansOfType(IUserAndAmountRiskService.class);
        if (!CollectionUtils.isEmpty(ruleNameAndServiceMap)) {
            ruleNameAndServiceMap.forEach((beanName, ruleService) -> RULE_SERVICE_MAP.put(ruleService.getRuleType(), ruleService));
        }
    }
}
