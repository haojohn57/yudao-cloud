package cn.iocoder.yudao.module.driver.service.impl;

import cn.iocoder.yudao.framework.driversdk.core.bean.mqtt.MessageHeader;
import cn.iocoder.yudao.module.driver.mqtt.service.MqttReceiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 益莲科技
 */
@Slf4j
@Service
public class MqttReceiveServiceImpl implements MqttReceiveService {

    @Override
    public void receiveValue(String client, MessageHeader messageHeader, String data) {
    }
}
