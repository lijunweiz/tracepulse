package io.github.lijunweiz.tracepulse.boot.actuate;

import io.github.lijunweiz.tracepulse.thread.ThreadAnalyzer;
import io.github.lijunweiz.tracepulse.thread.ThreadPoolInfo;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;

import java.util.Collections;
import java.util.Map;

/**
 * @author lijunwei
 */
@Endpoint(id = "tracepulse")
public class TracePulseEndPoint {

    private final ThreadAnalyzer threadAnalyzer;

    public TracePulseEndPoint(ThreadAnalyzer threadAnalyzer) {
        this.threadAnalyzer = threadAnalyzer;
    }

    @ReadOperation
    public Map<String, ThreadPoolInfo> threadPoolInfo() {
        return threadAnalyzer.getThreadPoolInfoCache();
    }

    @ReadOperation
    public Map<String, ThreadPoolInfo> threadPoolInfo(@Selector String toMatch) {
        return Collections.singletonMap(toMatch, threadPoolInfo().get(toMatch));
    }

}
