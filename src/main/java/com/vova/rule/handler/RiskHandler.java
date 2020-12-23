package com.vova.rule.handler;

import com.vova.rule.common.BaseResponse;
import com.vova.rule.common.request.RiskValidationRequest;
import com.vova.rule.common.vo.RuleValidationVo;
import com.vova.rule.service.domain.RuleEngineService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * risk handler
 *
 * @author itachy.zhu@gmail.com
 * @since 2020-12-23
 */
@RestController
@RequestMapping("/risk")
public class RiskHandler {

    @Resource
    private RuleEngineService ruleEngineService;

    @PostMapping("/validate")
    public BaseResponse<List<RuleValidationVo<BigDecimal>>> handleRisk(@RequestBody RiskValidationRequest request) {
        return ruleEngineService.validateRisk(request);
    }
}
