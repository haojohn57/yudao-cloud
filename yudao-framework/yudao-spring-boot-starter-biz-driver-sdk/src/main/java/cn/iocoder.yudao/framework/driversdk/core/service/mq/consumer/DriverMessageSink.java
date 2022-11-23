package cn.iocoder.yudao.framework.driversdk.core.service.mq.consumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 数据 Message 绑定
 *
 * @author 益莲科技
 */

public interface DriverMessageSink {

    @Input("driver-config-input-01")
    SubscribableChannel driverConfigInput();
}
