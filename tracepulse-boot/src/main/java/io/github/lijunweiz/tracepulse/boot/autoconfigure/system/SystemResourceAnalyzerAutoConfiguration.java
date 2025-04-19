package io.github.lijunweiz.tracepulse.boot.autoconfigure.system;

import io.github.lijunweiz.tracepulse.system.SystemResourceAnalyzer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lijunwei
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SystemResourceAnalyzerProperties.class)
@ConditionalOnProperty(prefix = "tracepulse.monitor.system", name = "enabled", havingValue = "true",
        matchIfMissing = true)
public class SystemResourceAnalyzerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SystemResourceAnalyzer systemResourceAnalyzer(SystemResourceAnalyzerProperties properties) {
        return new SystemResourceAnalyzer(properties);
    }

}
