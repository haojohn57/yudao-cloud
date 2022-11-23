package cn.iocoder.yudao.framework.driversdk.core.service.mq.producer;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 数据 Message 绑定
 *
 * @author 益莲科技
 */

public interface DriverMessageSource {

    @Output("driver-output-01")
    MessageChannel driverOutput();

    @Output("device-output")
    MessageChannel deviceOutput();

    @Output("point-output")
    MessageChannel pointOutput();
}
