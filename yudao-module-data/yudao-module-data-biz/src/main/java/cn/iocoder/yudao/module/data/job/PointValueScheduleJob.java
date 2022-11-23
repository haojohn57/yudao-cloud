package cn.iocoder.yudao.module.data.job;

import cn.iocoder.yudao.framework.common.iot.bean.point.PointValue;
import cn.iocoder.yudao.module.data.service.PointValueService;
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
public class PointValueScheduleJob  {

    @Value("${data.point.batch.speed}")
    private Integer batchSpeed;
    @Value("${data.point.batch.interval}")
    private Integer interval;

    @Resource
    private PointValueService pointValueService;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    public static List<PointValue> pointValues = new ArrayList<>();
    public static ReentrantReadWriteLock valueLock = new ReentrantReadWriteLock();
    public static AtomicLong valueCount = new AtomicLong(0), valueSpeed = new AtomicLong(0);

    @XxlJob("PointValueScheduleJob")
    protected void execute() {
        // Statistical point value receive rate
        long speed = valueCount.getAndSet(0);
        valueSpeed.set(speed);
        speed /= interval;
        if (speed >= batchSpeed) {
            log.debug("Point value receiver speed: {} /s, value size: {}, interval: {}", speed, pointValues.size(), interval);
        }

        // Save point value array to Redis & MongoDB
        threadPoolExecutor.execute(() -> {
            valueLock.writeLock().lock();
            if (pointValues.size() > 0) {
                pointValueService.savePointValues(pointValues);
                pointValues.clear();
            }
            valueLock.writeLock().unlock();
        });
    }
}
