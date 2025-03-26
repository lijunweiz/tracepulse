package io.github.lijunweiz.tracepulse.boot.actuate;

import io.github.lijunweiz.tracepulse.system.SystemResourceAnalyzer;
import io.github.lijunweiz.tracepulse.system.SystemResourceUsageRatio;
import io.github.lijunweiz.tracepulse.thread.ThreadAnalyzer;
import io.github.lijunweiz.tracepulse.thread.ThreadPoolInfo;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lijunwei
 */
@Endpoint(id = "tracepulse")
public class TracePulseEndPoint {

    private final ThreadAnalyzer threadAnalyzer;
    private final SystemResourceAnalyzer systemResourceAnalyzer;

    public TracePulseEndPoint(ThreadAnalyzer threadAnalyzer,
                              SystemResourceAnalyzer systemResourceAnalyzer) {
        this.threadAnalyzer = threadAnalyzer;
        this.systemResourceAnalyzer = systemResourceAnalyzer;
    }

    @ReadOperation
    public Object getCompositeData() {
        return this.getAnalyzerData();
    }

    @ReadOperation
    public Object getEntry(@Selector String key) {
        return this.getAnalyzerData().get(key);
    }

    private Map<String, Object> getAnalyzerData() {
        Map<String, Object> map = new HashMap<>();
        Map<String, ThreadPoolInfo> threadPoolInfoCache = threadAnalyzer.getThreadPoolInfoCache();
        SystemResourceUsageRatio usageRatioCache = systemResourceAnalyzer.getSystemResourceUsageRatioCache();
        map.put("thread", threadPoolInfoCache);
        map.put("system", usageRatioCache);

        return map;
    }

}
