package io.github.lijunweiz.tracepulse.boot.autoconfigure;

import io.github.lijunweiz.tracepulse.boot.actuate.TracePulseEndPoint;
import io.github.lijunweiz.tracepulse.system.SystemResourceAnalyzer;
import io.github.lijunweiz.tracepulse.thread.ThreadAnalyzer;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lijunwei
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnAvailableEndpoint(endpoint = TracePulseEndPoint.class)
public class TracePulseEndPointAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TracePulseEndPoint tracePulseEndPoint(ThreadAnalyzer threadAnalyzer,
                                                 SystemResourceAnalyzer systemResourceAnalyzer) {
        return new TracePulseEndPoint(threadAnalyzer, systemResourceAnalyzer);
    }

}
