package cn.iocoder.yudao.framework.driversdk.core.service.job;

import cn.iocoder.yudao.framework.common.iot.bean.driver.AttributeInfo;
import cn.iocoder.yudao.framework.common.iot.model.Device;
import cn.iocoder.yudao.framework.common.iot.model.Point;
import cn.iocoder.yudao.framework.driversdk.core.bean.driver.DriverContext;
import cn.iocoder.yudao.framework.driversdk.core.service.DriverCommandService;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Read Schedule Job
 *
 * @author 益莲科技
 */
@Component
public class DriverReadScheduleJob {

    @Resource
    private DriverContext driverContext;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;
    @Resource
    private DriverCommandService driverCommandService;

    @XxlJob("DriverReadScheduleJob")
    public void execute()  {
        Map<Long, Device> deviceMap = driverContext.getDriverMetadata().getDeviceMap();
        deviceMap.values().forEach(device -> {
            Set<Long> profileIds = device.getProfileIds();
            Map<Long, Map<String, AttributeInfo>> pointInfoMap = driverContext.getDriverMetadata().getPointInfoMap().get(device.getId());
            if (null != pointInfoMap && null != profileIds) {
                profileIds.forEach(profileId -> {
                    Map<Long, Point> pointMap = driverContext.getDriverMetadata().getProfilePointMap().get(profileId);
                    if (null != pointMap) {
                        pointMap.keySet().forEach(pointId -> {
                            Map<String, AttributeInfo> map = pointInfoMap.get(pointId);
                            if (null != map) {
                                threadPoolExecutor.execute(() -> driverCommandService.read(device.getId(), pointId));
                            }
                        });
                    }
                });
            }
        });
    }
}