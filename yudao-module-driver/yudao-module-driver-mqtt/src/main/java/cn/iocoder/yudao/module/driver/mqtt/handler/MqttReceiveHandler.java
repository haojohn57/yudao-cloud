package cn.iocoder.yudao.module.driver.mqtt.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.driversdk.core.bean.mqtt.MessageHeader;
import cn.iocoder.yudao.framework.driversdk.core.bean.mqtt.MessagePayload;
import cn.iocoder.yudao.framework.driversdk.core.bean.mqtt.MessageType;
import cn.iocoder.yudao.framework.driversdk.core.bean.mqtt.MqttMessage;
import cn.iocoder.yudao.framework.driversdk.core.utils.JsonUtil;
import cn.iocoder.yudao.module.driver.mqtt.job.MqttScheduleJob;
import cn.iocoder.yudao.module.driver.mqtt.service.MqttReceiveService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 益莲科技
 */
@Slf4j
@Configuration
public class MqttReceiveHandler {

    @Value("${driver.mqtt.batch.speed}")
    private Integer batchSpeed;

    @Resource
    private MqttReceiveService mqttReceiveService;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * 此处用于接收 MQTT 发送过来的数据，订阅的主题为 application.yml 中 mqtt.receive-topics 配置的 Topic 列表
     * +（加号）：可以（只能）匹配一个单词
     * #（井号）：可以匹配多个单词（或者零个）
     *
     * @return MessageHandler
     */
    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handlerValue() {
        return message -> {
            MessagePayload messagePayload = JsonUtil.parseObject(message.getPayload().toString(), MessagePayload.class);

            // 处理空字段
            // 当类型为空时，使用默认类型
            // 当消息载荷为空时，使用其 String 内容
            if (ObjectUtil.isNull(messagePayload)) {
                messagePayload = new MessagePayload(message.getPayload(), MessageType.DEFAULT);
            } else {
                if (StrUtil.isEmpty(messagePayload.getPayload())) messagePayload.setPayload(message.getPayload().toString());
                if (ObjectUtil.isNull(messagePayload.getMessageType())) messagePayload.setMessageType(MessageType.DEFAULT);
            }

            MessageHeader messageHeader = new MessageHeader(message.getHeaders());
            MqttMessage mqttMessage = new MqttMessage(messageHeader, messagePayload);

            // Judge whether to process data in batch according to the data transmission speed
            if (MqttScheduleJob.messageSpeed.get() < batchSpeed) {
                threadPoolExecutor.execute(() -> {
                    // Receive single mqtt message
                    mqttReceiveService.receiveValue(mqttMessage);
                });
            } else {
                // Save point value to schedule
                MqttScheduleJob.messageLock.writeLock().lock();
                MqttScheduleJob.mqttMessages.add(mqttMessage);
                MqttScheduleJob.messageLock.writeLock().unlock();
            }

        };
    }
}
