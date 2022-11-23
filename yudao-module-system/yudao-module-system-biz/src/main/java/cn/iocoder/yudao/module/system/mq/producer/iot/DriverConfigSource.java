package cn.iocoder.yudao.module.system.mq.producer.iot;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 数据 Message 绑定
 *
 * @author 益莲科技
 */

public interface DriverConfigSource {

    @Output("driver-config-output")
    MessageChannel driverConfigOutput();

}
