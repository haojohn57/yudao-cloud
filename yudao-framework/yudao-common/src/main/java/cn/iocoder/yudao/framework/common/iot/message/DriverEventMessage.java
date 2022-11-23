package cn.iocoder.yudao.framework.common.iot.message;

import cn.iocoder.yudao.framework.common.iot.model.DriverEvent;
import lombok.Data;

import javax.annotation.Resource;

/**
 * 驱动数据刷新 Message
 *
 * @author 益莲科技
 */
@Data
public class DriverEventMessage {

    @Resource
    private DriverEvent driverEvent;

    public DriverEventMessage() {
    }

    public DriverEventMessage(DriverEvent driverEvent) {
        this.driverEvent = driverEvent;
    }
}
