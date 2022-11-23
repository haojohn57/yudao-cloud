package cn.iocoder.yudao.module.system.mq.producer.iot;

import cn.iocoder.yudao.framework.common.iot.bean.driver.DriverConfiguration;
import cn.iocoder.yudao.framework.common.iot.message.DriverConfigMessage;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Driver 驱动相关消息的 Producer
 */
@Component
public class DriverProducer {

    @Resource
    DriverConfigSource messageSource1;
    public void sendDriverEvent(DriverConfiguration driverConfiguration){
        DriverConfigMessage message = new DriverConfigMessage(driverConfiguration);
        Message<DriverConfigMessage> messageS = MessageBuilder.withPayload(message).build();
        messageSource1.driverConfigOutput().send(messageS);
    }
}
