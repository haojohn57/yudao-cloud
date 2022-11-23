package cn.iocoder.yudao.module.driver.service.impl;

import cn.iocoder.yudao.framework.common.enums.DeviceConstants;
import cn.iocoder.yudao.framework.common.enums.DriverConstants;
import cn.iocoder.yudao.framework.common.iot.bean.driver.AttributeInfo;
import cn.iocoder.yudao.framework.common.iot.model.Device;
import cn.iocoder.yudao.framework.common.iot.model.Point;
import cn.iocoder.yudao.framework.driversdk.core.bean.driver.DriverContext;
import cn.iocoder.yudao.framework.driversdk.core.service.DriverCustomService;
import cn.iocoder.yudao.framework.driversdk.core.service.DriverServiceSdk;
import cn.iocoder.yudao.module.driver.mqtt.service.MqttSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

import static cn.iocoder.yudao.framework.driversdk.core.utils.DriverUtil.attribute;

/**
 * @author 益莲科技
 */
@Slf4j
@Service
public class DriverCustomServiceImpl implements DriverCustomService {

    @Resource
    private DriverContext driverContext;
    @Resource
    private DriverServiceSdk driverServiceSdk;
    @Resource
    private MqttSendService mqttSendService;

    @Override
    public void initial() {
    }

    @Override
    public String read(Map<String, AttributeInfo> driverInfo, Map<String, AttributeInfo> pointInfo, Device device, Point point) throws Exception {
        // 因为 MQTT 的数据来源是被动接收的，所以无需实现该 Read 方法
        // 接收数据处理函数在 com.dc3.driver.mqtt.handler.MqttReceiveHandler.handlerValue
        return "nil";
    }

    @Override
    public Boolean write(Map<String, AttributeInfo> driverInfo, Map<String, AttributeInfo> pointInfo, Device device, AttributeInfo values) throws Exception {
        String commandTopic = attribute(pointInfo, "commandTopic"), value = values.getValue();
        try {
            int commandQos = attribute(pointInfo, "commandQos");
            mqttSendService.sendToMqtt(commandTopic, commandQos, value);
        } catch (Exception e) {
            mqttSendService.sendToMqtt(commandTopic, value);
        }
        return true;
    }

    @Override
    public void schedule() {

        /*
        TODO:设备状态
        上传设备状态，可自行灵活拓展，不一定非要在schedule()接口中实现，也可以在read中实现设备状态的设置；
        你可以通过某种判断机制确定设备的状态，然后通过driverService.deviceEventSender接口将设备状态交给SDK管理。

        设备状态（DeviceStatus）如下：
        ONLINE:在线
        OFFLINE:离线
        MAINTAIN:维护
        FAULT:故障
         */
        driverContext.getDriverMetadata().getDeviceMap().keySet().forEach(id -> driverServiceSdk.deviceEventSender(id, DeviceConstants.Event.HEARTBEAT, DriverConstants.Status.ONLINE));
    }

}
