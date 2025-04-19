package io.github.lijunweiz.tracepulse;

import io.github.lijunweiz.tracepulse.boot.actuate.TracePulseEndPoint;
import io.github.lijunweiz.tracepulse.extension.dingtalk.DingTalkingMonitor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author lijunwei
 */
@Slf4j
@SpringBootApplication
public class TracepulseAutoConfigurationTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(TracepulseAutoConfigurationTest.class, args);
        log.info("{}", applicationContext.getBean(DingTalkingMonitor.class));
        log.info("{}", applicationContext.getBean(TracePulseEndPoint.class));
        System.exit(0);
    }

    @Test
    public void test() {
        Assertions.assertTrue(true);
    }

}
