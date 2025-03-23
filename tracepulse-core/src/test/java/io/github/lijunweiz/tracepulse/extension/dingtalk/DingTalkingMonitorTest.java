package io.github.lijunweiz.tracepulse.extension.dingtalk;

import io.github.lijunweiz.tracepulse.thread.SamplingProperties;
import io.github.lijunweiz.tracepulse.thread.NamedThreadFactory;
import io.github.lijunweiz.tracepulse.thread.ThreadAnalyzer;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class DingTalkingMonitorTest {

    @Test
    void ding() {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 2, 2, TimeUnit.MINUTES,
                new LinkedBlockingQueue<Runnable>(),
                new NamedThreadFactory("dingTest"));
        poolExecutor.execute(() -> System.out.println("1"));
        DingTalkingRobotProperties properties = new DingTalkingRobotProperties();
        properties.setWebhook("https://oapi.dingtalk.com/robot/send?access_token=xxxx");
        properties.setKeyWords("监控");
        Map<String, DingTalkingRobotProperties> dingTalkingRobotPropertiesMap = new HashMap<>();
        dingTalkingRobotPropertiesMap.put("apm", properties);

        ThreadAnalyzer threadAnalyzer = new ThreadAnalyzer(new SamplingProperties());
        DingTalkingMonitor talkingMonitor = new DingTalkingMonitor(dingTalkingRobotPropertiesMap, "apm");
        talkingMonitor.ding("线程信息", JSON.toJSONString(threadAnalyzer.threadPoolInfo(), JSONWriter.Feature.PrettyFormat));
    }
}