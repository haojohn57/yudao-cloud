package cn.iocoder.yudao.module.system.mq.consumer.iot;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.enums.CacheConstant;
import cn.iocoder.yudao.framework.common.enums.DriverConstants;
import cn.iocoder.yudao.framework.common.iot.bean.driver.DriverConfiguration;
import cn.iocoder.yudao.framework.common.iot.bean.driver.DriverRegister;
import cn.iocoder.yudao.framework.common.iot.message.DriverEventMessage;
import cn.iocoder.yudao.framework.common.iot.model.DriverEvent;
import cn.iocoder.yudao.module.system.mq.producer.iot.DriverProducer;
import cn.iocoder.yudao.module.system.service.iot.BatchService;
import cn.iocoder.yudao.module.system.service.iot.EventService;
import cn.iocoder.yudao.module.system.service.iot.driver.DriverSdkService;
import cn.iocoder.yudao.framework.common.util.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 针对 {@link DriverEventMessage} 的消费者
 *
 * @author 益莲科技
 */
@Component
@Slf4j
public class DriverEventConsumer  {

    @Resource
    private EventService eventService;

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private BatchService batchService;
    @Resource
    private DriverSdkService driverSdkService;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Resource
    private DriverProducer driverProducer;

    @StreamListener("driver-input")
    public void onMessage(DriverEventMessage message) {
        log.info("[execute][收到 驱动消息]");
        DriverEvent driverEvent = message.getDriverEvent();
        if (null == driverEvent || StrUtil.isEmpty(driverEvent.getServiceName())) {
            log.error("Invalid driver event {}", driverEvent);
            return;
        }

        log.debug("Driver {} event, Received: {}", driverEvent.getType(), driverEvent);
        switch (driverEvent.getType()) {
            case DriverConstants.Event.DRIVER_HANDSHAKE:
                DriverConfiguration driverConfiguration = new DriverConfiguration(
                        DriverConstants.Type.DRIVER,
                        DriverConstants.Event.DRIVER_HANDSHAKE_BACK,
                        null,
                        "ok"
                );
                driverProducer.sendDriverEvent(driverConfiguration);
                break;
            case DriverConstants.Event.DRIVER_REGISTER:
                driverConfiguration = new DriverConfiguration(
                        DriverConstants.Type.DRIVER,
                        DriverConstants.Event.DRIVER_REGISTER_BACK,
                        null,
                        "ok"
                );
                try {
                    driverSdkService.driverRegister(Convert.convert(DriverRegister.class, driverEvent.getContent()));
                } catch (Exception e) {
                    driverConfiguration.setResponse(e.getMessage());
                }
                driverProducer.sendDriverEvent(driverConfiguration);
                break;
            case DriverConstants.Event.DRIVER_METADATA_SYNC:
                driverConfiguration = new DriverConfiguration(
                        DriverConstants.Type.DRIVER,
                        DriverConstants.Event.DRIVER_METADATA_SYNC_BACK,
                        null,
                        "ok"
                );
                try {
                    driverConfiguration.setContent(batchService.batchDriverMetadata(driverEvent.getServiceName()));
                } catch (Exception e) {
                    driverConfiguration.setResponse(e.getMessage());
                }
                driverProducer.sendDriverEvent(driverConfiguration);
                break;
            case DriverConstants.Event.DRIVER_HEARTBEAT:
                redisUtil.setKey(
                        CacheConstant.Prefix.DRIVER_STATUS_KEY_PREFIX + driverEvent.getServiceName(),
                        driverEvent.getContent(),
                        driverEvent.getTimeOut(),
                        driverEvent.getTimeUnit()
                );
                break;
            case DriverConstants.Event.ERROR:
                //TODO 去重
                threadPoolExecutor.execute(() -> eventService.addDriverEvent(driverEvent));
            default:
                log.error("Invalid event type, {}", driverEvent.getType());
                break;
        }
    }

}
