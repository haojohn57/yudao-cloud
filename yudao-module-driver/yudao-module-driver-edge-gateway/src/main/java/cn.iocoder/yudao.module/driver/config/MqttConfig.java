package cn.iocoder.yudao.module.driver.config;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.enums.CacheConstant;
import cn.iocoder.yudao.framework.driversdk.core.bean.mqtt.MqttProperties;
import cn.iocoder.yudao.framework.driversdk.core.utils.X509Util;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import javax.annotation.Resource;
import java.util.ArrayList;


/**
 * @author 益莲科技
 */
@Slf4j
@Configuration
@IntegrationComponentScan
@EnableConfigurationProperties({MqttProperties.class})
public class MqttConfig {

    private static final String RANDOM_ID = CacheConstant.Symbol.UNDERSCORE + RandomUtil.randomString(8);

    @Resource
    private MqttProperties mqttProperties;

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel mqttOutputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer mqttInbound() {
        // set default receive topic
        String topicName = "dc3/mc/" + mqttProperties.getClient();
        if (null == mqttProperties.getReceiveTopics()) {
            mqttProperties.setReceiveTopics(new ArrayList<>());
        }
        boolean match = mqttProperties.getReceiveTopics().stream().anyMatch(topic -> topic.getName().equals(topicName));
        if (!match) {
            mqttProperties.getReceiveTopics().add(new MqttProperties.Topic(topicName, 2));
        }

        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                mqttProperties.getClient() + RANDOM_ID + "_in",
                mqttClientFactory(),
                mqttProperties.getReceiveTopics().stream().map(MqttProperties.Topic::getName).toArray(String[]::new));
        adapter.setQos(mqttProperties.getReceiveTopics().stream().mapToInt(MqttProperties.Topic::getQos).toArray());
        adapter.setOutputChannel(mqttInputChannel());
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setCompletionTimeout(mqttProperties.getCompletionTimeout());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutputChannel")
    public MessageHandler outbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(
                mqttProperties.getClient() + "_out",
                mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultQos(mqttProperties.getDefaultSendTopic().getQos());
        messageHandler.setDefaultTopic(mqttProperties.getDefaultSendTopic().getName());
        return messageHandler;
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectOptions());
        return factory;
    }

    @Bean
    public MqttConnectOptions getMqttConnectOptions() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();

        // username & password
        if (mqttProperties.getAuthType().equals(MqttProperties.AuthTypeEnum.USERNAME)) {
            mqttConnectOptions.setUserName(mqttProperties.getUsername());
            mqttConnectOptions.setPassword(mqttProperties.getPassword().toCharArray());
        }

        // tls x509
        if (mqttProperties.getAuthType().equals(MqttProperties.AuthTypeEnum.X509)) {
            mqttConnectOptions.setSocketFactory(X509Util.getSSLSocketFactory(
                    mqttProperties.getCaCrt(),
                    mqttProperties.getClientCrt(),
                    mqttProperties.getClientKey(),
                    StrUtil.isBlank(mqttProperties.getClientKeyPass()) ? "" : mqttProperties.getClientKeyPass()
            ));
            if (!StrUtil.isBlank(mqttProperties.getUsername()) && !StrUtil.isBlank(mqttProperties.getPassword())) {
                mqttConnectOptions.setUserName(mqttProperties.getUsername());
                mqttConnectOptions.setPassword(mqttProperties.getPassword().toCharArray());
            }
        }

        // disable https hostname verification
        mqttConnectOptions.setHttpsHostnameVerificationEnabled(false);
        mqttConnectOptions.setServerURIs(new String[]{mqttProperties.getUrl()});
        mqttConnectOptions.setKeepAliveInterval(mqttProperties.getKeepAlive());
        return mqttConnectOptions;

    }

}