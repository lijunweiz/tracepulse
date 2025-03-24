package io.github.lijunweiz.tracepulse.pr;

import lombok.Data;

/**
 * 物理资源使用占比
 * @author lijunwei
 */
@Data
public class PhysicalResourceUsageRatio {

    /**
     * 整个系统内存使用率[0.0, 1.0]，无法获取时返回-1
     */
    private double memoryUsageRatio = -1d;

    /**
     * 进程CPU使用率[0.0, 1.0]，无法获取时返回-1
     */
    private double processCpuLoad = -1d;

    /**
     * 系统CPU使用率[0.0, 1.0]，无法获取时返回-1
     */
    private double systemCpuLoad = -1d;

    /**
     * 堆内存使用率(0.0, 1.0]，无法获取时返回-1
     */
    private double jvmHeapMemoryUsageRatio = -1d;

    /**
     * 非堆内存使用率(0.0, 1.0]，无法获取时返回-1
     */
    private double jvmNonHeapMemoryUsageRatio = -1d;

}
