package cn.iocoder.yudao.framework.driversdk.core.service.mq.producer;

import cn.iocoder.yudao.framework.common.iot.bean.point.PointValue;
import cn.iocoder.yudao.framework.common.iot.message.PointMessage;
import cn.iocoder.yudao.framework.mq.core.bus.AbstractBusProducer;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 位号 相关消息的 Producer
 */
@Component
public class PointProducer extends AbstractBusProducer {

    /**
     * 发送 {@link PointMessage} 消息
     */
    @Resource
    DriverMessageSource driverMessageSource;
    public void sendPoint(PointValue pointValue) {
        PointMessage message = new PointMessage(pointValue);
        Message<PointMessage> messageS = MessageBuilder.withPayload(message).build();
        driverMessageSource.pointOutput().send(messageS);
    }

}
