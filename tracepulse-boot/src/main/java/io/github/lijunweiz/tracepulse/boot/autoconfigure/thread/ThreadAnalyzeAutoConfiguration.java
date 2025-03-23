package io.github.lijunweiz.tracepulse.boot.autoconfigure.thread;

import io.github.lijunweiz.tracepulse.thread.ThreadAnalyzer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author lijunwei
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ThreadAnalyzerProperties.class)
public class ThreadAnalyzeAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ThreadAnalyzer threadAnalyzer(ThreadAnalyzerProperties properties, Environment environment) {
        if (StringUtils.isBlank(properties.getName())) {
            properties.setName(environment.getProperty("spring.application.name"));
        }

        return new ThreadAnalyzer(properties);
    }

}
