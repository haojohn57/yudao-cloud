package cn.iocoder.yudao.framework.driversdk.core.service.job;

import cn.iocoder.yudao.framework.driversdk.core.service.DriverCustomService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 自定义调度任务
 *
 * @author 益莲科技
 */
@Slf4j
@Component
public class DriverCustomScheduleJob {
    @Resource
    private DriverCustomService driverCustomService;

    @XxlJob("DriverCustomScheduleJob")
    public void execute() {
        driverCustomService.schedule();
    }
}