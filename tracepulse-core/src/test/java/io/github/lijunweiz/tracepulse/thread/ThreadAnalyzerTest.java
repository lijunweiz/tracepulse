package io.github.lijunweiz.tracepulse.thread;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import io.github.lijunweiz.tracepulse.extension.dingtalk.DingTalkingMonitor;
import io.github.lijunweiz.tracepulse.extension.dingtalk.DingTalkingRobotProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ThreadAnalyzerTest {

    @Test
    void threadPoolInfo() {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 2, 2, TimeUnit.MINUTES,
                new LinkedBlockingQueue<Runnable>(),
                new NamedThreadFactory("dingTest", true));
        poolExecutor.execute(() -> System.out.println("1"));
        ThreadAnalyzer threadAnalyzer = new ThreadAnalyzer(new SamplingProperties());
        Map<String, ThreadPoolInfo> threadedPoolInfo = threadAnalyzer.threadPoolInfo();
        System.out.println(JSON.toJSONString(threadedPoolInfo, JSONWriter.Feature.PrettyFormat));
        Assertions.assertTrue(threadedPoolInfo.containsKey("dingTest"));
    }

}