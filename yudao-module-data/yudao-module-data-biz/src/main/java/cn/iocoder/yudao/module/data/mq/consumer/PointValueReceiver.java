package cn.iocoder.yudao.module.data.mq.consumer;

import cn.iocoder.yudao.framework.common.iot.bean.point.PointValue;
import cn.iocoder.yudao.module.data.job.PointValueScheduleJob;
import cn.iocoder.yudao.module.data.service.PointValueHandleService;
import cn.iocoder.yudao.module.data.service.PointValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 接收驱动发送过来的数据
 * <p>
 * 200万条SinglePointValue会产生：60M的索引数据以及400M的数据
 *
 * @author 益莲科技
 */
@Slf4j
@Component
public class PointValueReceiver {

    @Value("${data.point.batch.speed}")
    private Integer batchSpeed;

    @Resource
    private PointValueService pointValueService;
    @Resource
    private PointValueHandleService pointValueHandleService;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @StreamListener ("point-input-01")
    public void pointValueReceive(PointValue pointValue) throws IOException {
        if (null == pointValue || null == pointValue.getDeviceId()) {
            log.error("Invalid point value: {}", pointValue);
            return;
        }
        PointValueScheduleJob.valueCount.getAndIncrement();
        log.debug("Point value Received: {}", pointValue);

        // Judge whether to process data in batch according to the data transmission speed
        if (PointValueScheduleJob.valueSpeed.get() < batchSpeed) {
            threadPoolExecutor.execute(() -> {
                // Save point value to Redis & MongoDB
                pointValueService.savePointValue(pointValue);
            });
        } else {
            // Save point value to schedule
            PointValueScheduleJob.valueLock.writeLock().lock();
            PointValueScheduleJob.pointValues.add(pointValue);
            PointValueScheduleJob.valueLock.writeLock().unlock();
        }
    }
}
