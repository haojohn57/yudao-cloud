package cn.iocoder.yudao.module.data.mq.consumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 数据 Message 绑定
 *
 * @author 益莲科技
 */

public interface PointValueSink {

    @Input("point-input-01")
    SubscribableChannel pointInput();

}
