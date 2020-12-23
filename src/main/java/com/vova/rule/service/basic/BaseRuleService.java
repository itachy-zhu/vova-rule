package com.vova.rule.service.basic;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.vova.rule.common.request.RiskValidationRequest;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/**
 * abstract rule-service
 *
 * @author itachy.zhu@gmail.com
 * @since 2020-12-23
 */
@Getter
@Service
public class BaseRuleService {

    protected final Set<String> riskRequestHistoryCache = Sets.newHashSet();

    public final Map<Integer, BigDecimal> userRiskHistoryCache = Maps.newConcurrentMap();

    public void recordRiskHistory(RiskValidationRequest request) {

        if (riskRequestHistoryCache.contains(request.getRequestId())) {
            return;
        }
        synchronized (riskRequestHistoryCache) {
            if (riskRequestHistoryCache.contains(request.getRequestId())) {
                return;
            }
            riskRequestHistoryCache.add(request.getRequestId());
        }
        userRiskHistoryCache.put(request.getUserId(), request.getAmount().add(userRiskHistoryCache.getOrDefault(request.getUserId(), BigDecimal.ZERO)));
    }
}
