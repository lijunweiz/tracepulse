package io.github.lijunweiz.tracepulse.system;

import io.github.lijunweiz.tracepulse.thread.NamedThreadFactory;
import io.github.lijunweiz.tracepulse.thread.SamplingProperties;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 系统资源监控，内存、cpu
 * @author lijunwei
 */
public class SystemResourceAnalyzer {

    private SystemResourceUsageRatio systemResourceUsageRatioCache = null;

    public SystemResourceAnalyzer(SamplingProperties properties) {
        if (Objects.isNull(properties)) {
            properties = new SamplingProperties();
        }
        ScheduledExecutorService threadAnalyzer = Executors.newScheduledThreadPool(1,
                new NamedThreadFactory("MemoryMonitor", true));
        threadAnalyzer.scheduleWithFixedDelay(
                () -> this.systemResourceUsageRatioCache = this.systemResourceUsageRatio(),
                properties.getInitialDelay(),
                properties.getDelay(),
                TimeUnit.SECONDS
        );
    }

    public SystemResourceUsageRatio getSystemResourceUsageRatioCache() {
        return systemResourceUsageRatioCache;
    }

    public SystemResourceUsageRatio systemResourceUsageRatio() {
        java.lang.management.OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        SystemResourceUsageRatio usageRatio = new SystemResourceUsageRatio();
        if (operatingSystemMXBean instanceof com.sun.management.OperatingSystemMXBean) {
            com.sun.management.OperatingSystemMXBean sunOSMXBean =
                    (com.sun.management.OperatingSystemMXBean) operatingSystemMXBean;
            long totalPhysicalMemorySize = sunOSMXBean.getTotalPhysicalMemorySize();
            long freePhysicalMemorySize = sunOSMXBean.getFreePhysicalMemorySize();
            double memoryUsageRatio = (totalPhysicalMemorySize - freePhysicalMemorySize) /
                    (double) totalPhysicalMemorySize;
            memoryUsageRatio = BigDecimal.valueOf(memoryUsageRatio)
                    .setScale(4, RoundingMode.HALF_UP)
                    .doubleValue();
            usageRatio.setMemoryUsageRatio(memoryUsageRatio);

            double processCpuLoad = BigDecimal.valueOf(sunOSMXBean.getProcessCpuLoad())
                    .setScale(4, RoundingMode.HALF_UP)
                    .doubleValue();
            usageRatio.setProcessCpuLoad(processCpuLoad);
            double systemCpuLoad = BigDecimal.valueOf(sunOSMXBean.getSystemCpuLoad())
                    .setScale(4, RoundingMode.HALF_UP)
                    .doubleValue();
            usageRatio.setSystemCpuLoad(systemCpuLoad);
        }

        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        double jvmHeapMemoryUsageRatio = memoryMXBean.getHeapMemoryUsage().getUsed() /
                (double) memoryMXBean.getHeapMemoryUsage().getMax();
        jvmHeapMemoryUsageRatio = BigDecimal.valueOf(jvmHeapMemoryUsageRatio)
                .setScale(4, RoundingMode.HALF_UP)
                .doubleValue();
        usageRatio.setJvmHeapMemoryUsageRatio(jvmHeapMemoryUsageRatio);

        if (memoryMXBean.getNonHeapMemoryUsage().getMax() != -1) {
            double jvmNonHeapMemoryUsageRatio = memoryMXBean.getNonHeapMemoryUsage().getUsed() /
                    (double) memoryMXBean.getNonHeapMemoryUsage().getMax();
            jvmNonHeapMemoryUsageRatio = BigDecimal.valueOf(jvmNonHeapMemoryUsageRatio)
                    .setScale(4, RoundingMode.HALF_UP)
                    .doubleValue();
            usageRatio.setJvmNonHeapMemoryUsageRatio(jvmNonHeapMemoryUsageRatio);
        }

        return usageRatio;
    }

}
