package cn.iocoder.yudao.module.driver;

import cn.iocoder.yudao.framework.driversdk.core.service.mq.consumer.DriverMessageSink;
import cn.iocoder.yudao.framework.driversdk.core.service.mq.producer.DriverMessageSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @author 益莲科技
 */
@EnableCaching
@EnableBinding({DriverMessageSink.class, DriverMessageSource.class})
@SpringBootApplication
public class MqttDriverApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqttDriverApplication.class, args);
    }
}

