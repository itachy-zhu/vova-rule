package com.vova.rule.common.configuration;

import com.ql.util.express.ExpressRunner;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * config-bean
 *
 * @author itachy.zhu@gmail.com
 * @since 2020-12-23
 */
@Configuration
public class CommonBean {

    @Bean
    public RulesEngine easyRuleEngine() {
        return new DefaultRulesEngine();
    }

    @Bean
    public ExpressRunner expressRunner() {
        return new ExpressRunner();
    }
}
