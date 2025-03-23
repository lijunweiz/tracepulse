package io.github.lijunweiz.tracepulse.extension.dingtalk;

import lombok.Data;

/**
 * @author lijunwei
 */
@Data
public class DingTalkingRobotProperties {

    /**
     * 示例：https://oapi.dingtalk.com/robot/send?access_token=xxxxxx
     */
    private String webhook;

    /**
     * 钉钉消息匹配关键字，详见dingtalk文档
     */
    private String keyWords;

    private String msgtype = "text";

}
