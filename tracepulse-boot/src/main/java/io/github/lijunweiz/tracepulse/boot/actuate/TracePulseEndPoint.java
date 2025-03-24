package io.github.lijunweiz.tracepulse.boot.actuate;

import io.github.lijunweiz.tracepulse.pr.PhysicalResourceAnalyzer;
import io.github.lijunweiz.tracepulse.pr.PhysicalResourceUsageRatio;
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
    private final PhysicalResourceAnalyzer physicalResourceAnalyzer;

    public TracePulseEndPoint(ThreadAnalyzer threadAnalyzer,
                              PhysicalResourceAnalyzer physicalResourceAnalyzer) {
        this.threadAnalyzer = threadAnalyzer;
        this.physicalResourceAnalyzer = physicalResourceAnalyzer;
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
        PhysicalResourceUsageRatio usageRatioCache = physicalResourceAnalyzer.getPhysicalResourceUsageRatioCache();
        map.put("thread", threadPoolInfoCache);
        map.put("pr", usageRatioCache);

        return map;
    }

}
