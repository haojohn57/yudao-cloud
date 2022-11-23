package cn.iocoder.yudao.framework.common.iot.message;

import cn.iocoder.yudao.framework.common.iot.model.DeviceEvent;
import lombok.Data;

import javax.annotation.Resource;

/**
 * 设备数据刷新 Message
 *
 * @author 益莲科技
 */
@Data
public class DeviceEventMessage {

    @Resource
    private DeviceEvent deviceEvent;
    public DeviceEventMessage() {
    }

    public DeviceEventMessage(DeviceEvent deviceEvent) {
        this.deviceEvent = deviceEvent;
    }
}
