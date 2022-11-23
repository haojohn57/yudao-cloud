package cn.iocoder.yudao.module.driver.mqtt.service;


import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * @author 益莲科技
 */
public interface MqttSendService {

    /**
     * Use Default Topic & Default Qos Send Data
     *
     * @param data string
     */
    void sendToMqtt(String data);

    /**
     * Use Default Topic & Custom Qos Send Data
     *
     * @param qos  Custom Qos
     * @param data string
     */
    void sendToMqtt(@Header(MqttHeaders.QOS) Integer qos, String data);

    /**
     * Use Custom Topic & Default Qos Send Data
     *
     * @param topic Custom Topic
     * @param data  string
     */
    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, String data);

    /**
     * Use Custom Topic & Custom Qos Send Data
     *
     * @param topic Custom Topic
     * @param qos   Custom Qos
     * @param data  string
     */
    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) Integer qos, String data);
}
