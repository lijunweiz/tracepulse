package io.github.lijunweiz.tracepulse.boot.autoconfigure.pr;

import io.github.lijunweiz.tracepulse.pr.PhysicalResourceAnalyzer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lijunwei
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(PhysicalResourceAnalyzerProperties.class)
public class PhysicalResourceAnalyzerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public PhysicalResourceAnalyzer physicalResourceAnalyzer(PhysicalResourceAnalyzerProperties properties) {
        return new PhysicalResourceAnalyzer(properties);
    }

}
