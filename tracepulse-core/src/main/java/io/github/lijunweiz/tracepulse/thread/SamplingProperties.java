package io.github.lijunweiz.tracepulse.thread;

import lombok.Data;

/**
 * @author lijunwei
 */
@Data
public class SamplingProperties {
    /**
     * 系统名称
     */
    private String name = "default";

    /**
     * 是否采样
     */
    private boolean enabled = true;

    /**
     * 采样初始延迟时间,单位秒,默认10秒
     */
    private int initialDelay = 10;

    /**
     * 采样延迟时间间隔,单位秒,默认5秒
     */
    private int delay = 5;
}
