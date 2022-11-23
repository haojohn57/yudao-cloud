package cn.iocoder.yudao.module.data;

import cn.iocoder.yudao.module.data.mq.consumer.PointValueSink;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * 消息中心中心服务启动入口
 *
 * @author 益莲科技
 */
@SpringBootApplication(scanBasePackages = {"cn.iocoder.yudao.framework.common","cn.iocoder.yudao.module.system.*"})
@EnableBinding(PointValueSink.class)
public class DataApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataApplication.class, args);
    }
}

