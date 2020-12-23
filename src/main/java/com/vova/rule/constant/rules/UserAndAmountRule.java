package com.vova.rule.constant.rules;

import com.vova.rule.common.configuration.BlockListStore;
import com.vova.rule.common.vo.BlockReasonVo;
import com.vova.rule.constant.enums.RuleTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

import java.math.BigDecimal;

/**
 * user and amount rule
 *
 * @author itachy.zhu@gmail.com
 * @since 2020-12-23
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Rule(name = "user-amount rule", description = "if total amount > 100 then freeze user")
public class UserAndAmountRule {

    private Integer userId;
    private BigDecimal amount;
    private BlockListStore<BigDecimal> blockListStore;

    @Condition
    public boolean amountMoreThan100(@Fact("moreThan") boolean moreThan) {
        return moreThan;
    }

    @Action
    public void freezeUser() {
        blockListStore.setBlockReason(userId, BlockReasonVo.<BigDecimal>builder().userId(userId).key(amount).type(RuleTypeEnum.EASY_RULE.getType()).rule("more than 100").reason("total amount > 100").build());
    }
}
