package cn.iocoder.yudao.module.driver.mqtt.job;

import cn.iocoder.yudao.framework.driversdk.core.bean.mqtt.MqttMessage;
import cn.iocoder.yudao.module.driver.mqtt.service.MqttReceiveService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author 益莲科技
 */
@Slf4j
@Component
public class MqttScheduleJob {

    public static List<MqttMessage> mqttMessages = new ArrayList<>();
    public static ReentrantReadWriteLock messageLock = new ReentrantReadWriteLock();
    public static AtomicLong messageCount = new AtomicLong(0), messageSpeed = new AtomicLong(0);
    @Value("${driver.mqtt.batch.speed}")
    private Integer batchSpeed;
    @Value("${driver.mqtt.batch.interval}")
    private Integer interval;
    @Resource
    private MqttReceiveService mqttReceiveService;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @XxlJob("MqttScheduleJob")
    protected void execute() {
        // Statistical mqtt message receive rate
        long speed = messageCount.getAndSet(0);
        messageSpeed.set(speed);
        speed /= interval;
        if (speed >= batchSpeed) {
            log.debug("Mqtt message receiver speed: {} /s, value size: {}, interval: {}", speed, mqttMessages.size(), interval);
        }

        // Receive batch mqtt message
        threadPoolExecutor.execute(() -> {
            messageLock.writeLock().lock();
            if (mqttMessages.size() > 0) {
                mqttReceiveService.receiveValues(mqttMessages);
                mqttMessages.clear();
            }
            messageLock.writeLock().unlock();
        });
    }
}
