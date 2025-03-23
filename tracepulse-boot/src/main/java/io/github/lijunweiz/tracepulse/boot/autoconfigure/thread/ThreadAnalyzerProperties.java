package io.github.lijunweiz.tracepulse.boot.autoconfigure.thread;

import io.github.lijunweiz.tracepulse.thread.SamplingProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 线程分析器配置
 * @author lijunwei
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = "tracepulse.monitor.thread")
public class ThreadAnalyzerProperties extends SamplingProperties {

    @Value("${spring.application.name}")
    private String name;

}
