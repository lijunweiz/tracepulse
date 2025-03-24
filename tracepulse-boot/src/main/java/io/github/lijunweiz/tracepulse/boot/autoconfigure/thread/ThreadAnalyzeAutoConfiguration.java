package io.github.lijunweiz.tracepulse.boot.autoconfigure.thread;

import io.github.lijunweiz.tracepulse.thread.ThreadAnalyzer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lijunwei
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ThreadAnalyzerProperties.class)
public class ThreadAnalyzeAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ThreadAnalyzer threadAnalyzer(ThreadAnalyzerProperties properties) {
        return new ThreadAnalyzer(properties);
    }

}
