package io.github.lijunweiz.tracepulse.boot.autoconfigure.dingtalk;

import io.github.lijunweiz.tracepulse.extension.dingtalk.DingTalkingRobotProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * 钉钉机器人配置
 * @author lijunwei
 */
@Data
@ConfigurationProperties(prefix = "tracepulse.dingtalk")
public class DingTalkProperties {

    /**
     * 是否启用钉钉机器人
     */
    private Boolean enabled = true;

    /**
     * 默认启用的机器人名称，其名称是{@link #robot}元素的key
     */
    private String defaultRobot;

    /**
     * 机器人列表，key为机器人名称，value为机器人具体配置
     */
    private Map<String, DingTalkingRobotProperties> robot;

}
