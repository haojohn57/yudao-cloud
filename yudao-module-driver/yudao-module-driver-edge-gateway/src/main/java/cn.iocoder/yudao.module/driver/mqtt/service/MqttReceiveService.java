package cn.iocoder.yudao.module.driver.mqtt.service;


import cn.iocoder.yudao.framework.driversdk.core.bean.mqtt.MessageHeader;

/**
 * @author 益莲科技
 */
public interface MqttReceiveService {

    /**
     * receive opcua value
     *
     * @param client        String
     * @param messageHeader MessageHeader
     * @param data          String
     */
    void receiveValue(String client, MessageHeader messageHeader, String data);
}
