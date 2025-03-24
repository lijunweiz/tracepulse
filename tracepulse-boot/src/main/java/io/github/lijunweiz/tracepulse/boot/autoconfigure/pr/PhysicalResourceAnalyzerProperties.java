package io.github.lijunweiz.tracepulse.boot.autoconfigure.pr;

import io.github.lijunweiz.tracepulse.thread.SamplingProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 内存、cpu配置
 * @author lijunwei
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = "tracepulse.monitor.pr")
public class PhysicalResourceAnalyzerProperties extends SamplingProperties {

    @Value("${spring.application.name:default}")
    private String name;

}
