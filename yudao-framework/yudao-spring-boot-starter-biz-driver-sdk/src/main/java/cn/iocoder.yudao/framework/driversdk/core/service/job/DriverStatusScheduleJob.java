package cn.iocoder.yudao.framework.driversdk.core.service.job;

import cn.iocoder.yudao.framework.common.enums.DriverConstants;
import cn.iocoder.yudao.framework.common.iot.model.DriverEvent;
import cn.iocoder.yudao.framework.driversdk.core.bean.driver.DriverContext;
import cn.iocoder.yudao.framework.driversdk.core.service.DriverServiceSdk;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 自定义调度任务
 *
 * @author 益莲科技
 */

@Component
public class DriverStatusScheduleJob {

    @Value("${spring.application.name}")
    private String serviceName;
    @Resource
    private DriverContext driverContext;
    @Resource
    private DriverServiceSdk driverSdkService;


    @XxlJob("DriverStatusScheduleJob")
    public void execute() {
        DriverEvent driverEvent = new DriverEvent(serviceName, DriverConstants.Event.DRIVER_HEARTBEAT, driverContext.getDriverStatus(), 10, TimeUnit.SECONDS);
        driverSdkService.driverEventSender(driverEvent);
    }
}