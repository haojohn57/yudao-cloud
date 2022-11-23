package cn.iocoder.yudao.module.system.mq.consumer.iot;

import cn.iocoder.yudao.framework.common.enums.CacheConstant;
import cn.iocoder.yudao.framework.common.enums.DeviceConstants;
import cn.iocoder.yudao.framework.common.iot.message.DeviceEventMessage;
import cn.iocoder.yudao.module.system.service.iot.EventService;
import cn.iocoder.yudao.framework.common.util.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * 针对 {@link DeviceEventMessage} 的消费者
 *
 * @author 益莲科技
 */
@Component
@Slf4j
public class DeviceEventConsumer {

    @Resource
    private EventService eventService;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;
    @Resource
    //private RedisTemplate redisTemplate;
    private RedisUtil redisUtil;

    @StreamListener("device-input")
    public void onMessage(DeviceEventMessage message) {
        log.info("[execute][收到 Dept 刷新消息]");
        switch (message.getDeviceEvent().getType()) {
            // Save device heartbeat to Redis
            case DeviceConstants.Event.HEARTBEAT:
                redisUtil.setKey(
                        CacheConstant.Prefix.DEVICE_STATUS_KEY_PREFIX + message.getDeviceEvent().getDeviceId(),
                        message.getDeviceEvent().getContent(),
                        message.getDeviceEvent().getTimeOut(),
                        message.getDeviceEvent().getTimeUnit()
                );
                break;
            case DeviceConstants.Event.ERROR:
            case DeviceConstants.Event.OVER_UPPER_LIMIT:
            case DeviceConstants.Event.OVER_LOWER_LIMIT:
                //TODO 去重
                threadPoolExecutor.execute(() -> eventService.addDeviceEvent(message.getDeviceEvent()));
                break;
            default:
                log.error("Invalid event type, {}", message.getDeviceEvent().getType());
                break;
        }
    }

}
