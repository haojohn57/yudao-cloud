package cn.iocoder.yudao.module.driver.mqtt.service.impl;

import cn.iocoder.yudao.framework.driversdk.core.bean.mqtt.MqttMessage;
import cn.iocoder.yudao.module.driver.mqtt.service.MqttReceiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 益莲科技
 */
@Slf4j
@Service
public class MqttReceiveServiceImpl implements MqttReceiveService {

    @Override
    public void receiveValue(MqttMessage mqttMessage) {
    }

    @Override
    public void receiveValues(List<MqttMessage> mqttMessageList) {

    }
}
