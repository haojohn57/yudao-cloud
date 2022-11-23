package cn.iocoder.yudao.framework.driversdk.core.service.mq.producer;

import cn.iocoder.yudao.framework.common.iot.message.DeviceEventMessage;
import cn.iocoder.yudao.framework.common.iot.model.DeviceEvent;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Device 设备相关消息的 Producer
 */
@Component
public class DeviceProducer {

    /**
     * 发送 {@link DeviceEventMessage} 消息
     */
    @Resource
    DriverMessageSource driverMessageSource;
    public void sendDeviceEvent(DeviceEvent deviceEvent) {
        DeviceEventMessage message = new DeviceEventMessage(deviceEvent);
        message.setDeviceEvent(deviceEvent);
        Message<DeviceEventMessage> messageS = MessageBuilder.withPayload(message).build();
        driverMessageSource.deviceOutput().send(messageS);
    }

}
