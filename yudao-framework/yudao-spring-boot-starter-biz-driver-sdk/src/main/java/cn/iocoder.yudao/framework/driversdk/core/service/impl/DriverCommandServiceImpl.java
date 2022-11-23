package cn.iocoder.yudao.framework.driversdk.core.service.impl;

import cn.iocoder.yudao.framework.common.iot.bean.driver.AttributeInfo;
import cn.iocoder.yudao.framework.common.iot.bean.point.PointValue;
import cn.iocoder.yudao.framework.common.iot.model.Device;
import cn.iocoder.yudao.framework.driversdk.core.bean.driver.DriverContext;
import cn.iocoder.yudao.framework.driversdk.core.service.DriverCommandService;
import cn.iocoder.yudao.framework.driversdk.core.service.DriverCustomService;
import cn.iocoder.yudao.framework.driversdk.core.service.DriverServiceSdk;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 益莲科技
 */
@Slf4j
@Service
public class DriverCommandServiceImpl implements DriverCommandService {

    @Resource
    private DriverContext driverContext;
    @Resource
    private DriverServiceSdk driverSdkService;
    @Resource
    private DriverCustomService driverCustomService;

    @Override
    public PointValue read(Long deviceId, Long pointId) {
        Device device = driverContext.getDeviceByDeviceId(deviceId);

        try {
            String rawValue = driverCustomService.read(
                    driverContext.getDriverInfoByDeviceId(deviceId),
                    driverContext.getPointInfoByDeviceIdAndPointId(deviceId, pointId),
                    device,
                    driverContext.getPointByDeviceIdAndPointId(deviceId, pointId)
            );

            PointValue pointValue = new PointValue(deviceId, pointId, rawValue, driverSdkService.convertValue(deviceId, pointId, rawValue));
            driverSdkService.pointValueSender(pointValue);
            return pointValue;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean write(Long deviceId, Long pointId, String value) {
        Device device = driverContext.getDeviceByDeviceId(deviceId);
        try {
            return driverCustomService.write(
                    driverContext.getDriverInfoByDeviceId(deviceId),
                    driverContext.getPointInfoByDeviceIdAndPointId(deviceId, pointId),
                    device,
                    new AttributeInfo(value, driverContext.getPointByDeviceIdAndPointId(deviceId, pointId).getType())
            );
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

}
