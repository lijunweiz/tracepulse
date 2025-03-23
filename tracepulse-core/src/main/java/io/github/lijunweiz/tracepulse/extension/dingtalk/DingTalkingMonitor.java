package io.github.lijunweiz.tracepulse.extension.dingtalk;

import com.alibaba.fastjson2.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 只实现使用access_token根据关键字匹配进行钉钉消息通知，且消息格式为markdown或text，默认使用text格式，
 * webhook地址包含access_token
 * @author lijunwei
 */
@Slf4j
public class DingTalkingMonitor {

    private final Map<String, DingTalkClient> dingTalkClientMap = new HashMap<>();

    private final Map<String, DingTalkingRobotProperties> dingTalkingRobotPropertiesMap;

    /**
     * 默认机器人名称
     */
    private String defaultRobot = "default";

    public DingTalkingMonitor(Map<String, DingTalkingRobotProperties> dingTalkingRobotPropertiesMap,
                              String defaultRobot) {
        this.dingTalkingRobotPropertiesMap = dingTalkingRobotPropertiesMap;
        this.defaultRobot = defaultRobot;
    }

    /**
     * 使用默认钉钉机器人发送消息
     * @param title 消息标题
     * @param message 消息内容
     */
    public void ding(String title, String message) {
        this.ding(this.defaultRobot, title, message);
    }

    /**
     * 钉钉机器人发送消息
     * @param robotName 机器人名称
     * @param title 消息标题
     * @param message 消息内容
     */
    public void ding(String robotName, String title, String message) {
        if (StringUtils.isBlank(robotName)) {
            log.error("robotName不能为空");
            return;
        }

        DingTalkingRobotProperties properties = dingTalkingRobotPropertiesMap.get(robotName);
        if (Objects.isNull(properties)) {
            log.error("找不到robot: {}", robotName);
            return;
        }

        try {
            DingTalkClient client = this.getDingTalkClient(properties.getWebhook());
            OapiRobotSendRequest request = this.getOapiRobotSendRequest(properties, title, message);
            OapiRobotSendResponse response = client.execute(request);
            if (response.getErrcode() != 0) {
                log.error("钉钉机器人发送失败, 返回信息: {}", JSON.toJSONString(response));
            }
        } catch (ApiException e) {
            log.error("钉钉机器人发送失败, 通讯异常：{}", e.getMessage());
        }
    }

    private synchronized DingTalkClient getDingTalkClient(String webhook) {
        if (dingTalkClientMap.containsKey(webhook)) {
            return dingTalkClientMap.get(webhook);
        }

        DingTalkClient dingTalkClient = new DefaultDingTalkClient(webhook);
        dingTalkClientMap.put(webhook, dingTalkClient);
        return dingTalkClient;
    }

    private String generateSignature(Long timestamp, String secret) {
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.warn("钉钉机器人初始化秘钥失败：{}", e.getMessage());
            return null;
        }

        byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        return URLEncoder.encode(new String(Base64.getEncoder().encode(signData)), StandardCharsets.UTF_8);
    }

    private OapiRobotSendRequest getOapiRobotSendRequest(DingTalkingRobotProperties properties, String title, String message) {
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype(properties.getMsgtype());
        if (Objects.equals("markdown", properties.getMsgtype())) {
            request.setMarkdown(this.getMarkdown(properties.getKeyWords(), title, message));
        } else {
            request.setText(this.getText(properties.getKeyWords(), title, message));
        }

        return request;
    }

    private OapiRobotSendRequest.Text getText(String keyWords, String title, String content) {
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(String.format("%s%n%s", title + keyWords, content));
        return text;
    }

    private OapiRobotSendRequest.Markdown getMarkdown(String keyWords, String title, String content) {
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        markdown.setTitle(title + keyWords);
        markdown.setText(content);
        return markdown;
    }

}
