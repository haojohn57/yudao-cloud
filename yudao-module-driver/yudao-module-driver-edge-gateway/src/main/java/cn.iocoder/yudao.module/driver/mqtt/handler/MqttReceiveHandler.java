package cn.iocoder.yudao.module.driver.mqtt.handler;

import cn.iocoder.yudao.framework.common.util.json.JsonUtils;
import cn.iocoder.yudao.framework.driversdk.core.bean.mqtt.MessageHeader;
import cn.iocoder.yudao.module.driver.mqtt.bean.MqttPayload;
import cn.iocoder.yudao.module.driver.mqtt.service.MqttReceiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;

import javax.annotation.Resource;

/**
 * @author 益莲科技
 */
@Slf4j
@Configuration
public class MqttReceiveHandler {

    @Resource
    private MqttReceiveService receiveService;

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handlerValue() {
        return message -> {
            MessageHeader messageHeader = new MessageHeader(message.getHeaders());
            MqttPayload payload = JsonUtils.parseObject(message.getPayload().toString(), MqttPayload.class);
        };
    }
}
