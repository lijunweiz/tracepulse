package io.github.lijunweiz.tracepulse.system;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import io.github.lijunweiz.tracepulse.thread.SamplingProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class PhysicalResourceAnalyzerTest {

    @Test
    void physicalResourceUsageRatio() {
        ArrayList<Object> objects = new ArrayList<>(1000000);
        for (int i = 0; i < 1000000; i++) {
            objects.add(new Object());
        }
        SystemResourceAnalyzer systemResourceAnalyzer = new SystemResourceAnalyzer(new SamplingProperties());
        SystemResourceUsageRatio usageRatio = systemResourceAnalyzer.systemResourceUsageRatio();
        System.out.println(JSON.toJSONString(usageRatio, JSONWriter.Feature.PrettyFormat));
        Assertions.assertTrue(usageRatio.getSystemCpuLoad() >= 0);
    }

}