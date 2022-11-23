package cn.iocoder.yudao.module.driver.mqtt.service;

import cn.iocoder.yudao.framework.driversdk.core.bean.mqtt.MqttMessage;

import java.util.List;

/**
 * @author 益莲科技
 */
public interface MqttReceiveService {

    /**
     * 务必实现，单点逻辑
     * <p>
     * 将解析之后的数据封装 com.dc3.common.bean.point.PointValue
     * 然后调用 driverService.pointValueSender(pointValue) 进行数据推送
     * Tip： 可参考 dc3-driver-listening-virtual 驱动
     *
     * @param mqttMessage MqttMessage
     */
    void receiveValue(MqttMessage mqttMessage);

    /**
     * 务必实现，批量逻辑
     * <p>
     * 将解析之后的数据封装 com.dc3.common.bean.point.PointValue
     * 然后调用 driverService.pointValueSender(pointValue) 进行数据推送
     * Tip： 可参考 dc3-driver-listening-virtual 驱动
     *
     * @param mqttMessageList String Array List
     */
    void receiveValues(List<MqttMessage> mqttMessageList);
}
