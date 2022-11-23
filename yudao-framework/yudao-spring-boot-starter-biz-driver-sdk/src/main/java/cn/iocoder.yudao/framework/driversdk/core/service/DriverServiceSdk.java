package cn.iocoder.yudao.framework.driversdk.core.service;

import cn.iocoder.yudao.framework.common.iot.bean.point.PointValue;
import cn.iocoder.yudao.framework.common.iot.model.DeviceEvent;
import cn.iocoder.yudao.framework.common.iot.model.DriverEvent;

import java.util.List;

/**
 * @author 益莲科技
 */
public interface DriverServiceSdk {

    /**
     * 将位号原始值进行处理和转换
     *
     * @param deviceId Device Id
     * @param pointId  Point Id
     * @param rawValue Raw Value
     * @return PointValue
     */
    String convertValue(Long deviceId, Long pointId, String rawValue);

    /**
     * 发送驱动事件
     *
     * @param driverEvent Driver Event
     */
    void driverEventSender(DriverEvent driverEvent);

    /**
     * 发送设备事件
     *
     * @param deviceEvent Device Event
     */
    void deviceEventSender(DeviceEvent deviceEvent);

    /**
     * 发送设备事件
     *
     * @param deviceId Device Id
     * @param type     Event Type, STATUS、LIMIT
     * @param content  Event Content
     */
    void deviceEventSender(Long deviceId, String type, String content);

    /**
     * 发送设备事件
     *
     * @param deviceId Device Id
     * @param pointId  Point Id
     * @param type     Event Type, STATUS、LIMIT
     * @param content  Event Content
     */
    void deviceEventSender(Long deviceId, Long pointId, String type, String content);

    /**
     * 发送位号值到消息组件
     *
     * @param pointValue PointValue
     */
    void pointValueSender(PointValue pointValue);

    /**
     * 批量发送位号值到消息组件
     *
     * @param pointValues PointValue Array
     */
    void pointValueSender(List<PointValue> pointValues);

    /**
     * Close ApplicationContext
     *
     * @param template Template
     * @param params   Object Params
     */
    void close(CharSequence template, Object... params);

}
