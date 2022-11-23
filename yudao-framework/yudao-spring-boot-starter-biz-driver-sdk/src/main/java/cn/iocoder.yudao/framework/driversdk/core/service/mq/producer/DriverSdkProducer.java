package cn.iocoder.yudao.framework.driversdk.core.service.mq.producer;

import cn.iocoder.yudao.framework.common.iot.message.DriverEventMessage;
import cn.iocoder.yudao.framework.common.iot.model.DriverEvent;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
/**
 * Driver 驱动相关消息的 Producer
 */

@Component
public class DriverSdkProducer {

    /**
     * 发送 {@link DriverEventMessage} 消息
    */
    @Resource
    DriverMessageSource driverMessageSource;
    public void sendDriverEvent( DriverEvent driverEvent) {
        //publishEvent(new DriverEventMessage(this, getBusId(), selfDestinationService(),driverEvent));
        DriverEventMessage message = new DriverEventMessage(driverEvent);
        message.setDriverEvent(driverEvent);
        Message<DriverEventMessage> messageS = MessageBuilder.withPayload(message).build();
        driverMessageSource.driverOutput().send(messageS);
    }

}
