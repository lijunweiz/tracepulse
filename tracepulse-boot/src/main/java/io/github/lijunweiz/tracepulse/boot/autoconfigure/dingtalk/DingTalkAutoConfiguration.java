package io.github.lijunweiz.tracepulse.boot.autoconfigure.dingtalk;

import io.github.lijunweiz.tracepulse.extension.dingtalk.DingTalkingMonitor;
import com.dingtalk.api.DingTalkClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lijunwei
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(DingTalkClient.class)
@EnableConfigurationProperties(DingTalkProperties.class)
@ConditionalOnProperty(prefix = "tracepulse.dingtalk", name = "enabled", havingValue = "true",
        matchIfMissing = true)
public class DingTalkAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DingTalkingMonitor dingTalkingMonitor(DingTalkProperties properties) {
        return new DingTalkingMonitor(properties.getRobot(), properties.getDefaultRobot());
    }

}
