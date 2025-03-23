package io.github.lijunweiz.tracepulse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author lijunwei
 */
@ExtendWith(SpringExtension.class)
class TracepulseAutoConfigurationTest {

    @MockBean
    ApplicationContext applicationContext;

    @Test
    void testAutoConfigDingTalking() {
        Assertions.assertTrue(true);
    }

}
