package cn.iocoder.yudao.module.system.service.iot;

import cn.iocoder.yudao.framework.common.enums.DriverConstants;
import cn.iocoder.yudao.framework.common.iot.bean.driver.DriverConfiguration;
import cn.iocoder.yudao.framework.common.iot.model.DriverInfo;
import cn.iocoder.yudao.framework.common.iot.model.PointInfo;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.device.DeviceDO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.driver.DriverDO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.point.PointDO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.profile.ProfileDO;
import cn.iocoder.yudao.module.system.mq.producer.iot.DriverProducer;
import cn.iocoder.yudao.module.system.service.iot.driver.DriverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * NotifyService Impl
 *
 * @author 益莲科技
 */
@Slf4j
@Service
public class NotifyServiceImpl implements NotifyService {

    @Resource
    private DriverService driverService;
    @Resource
    private DriverProducer driverProducer;

    @Override
    public void notifyDriverProfile(String command, ProfileDO profile) {
        try {
            List<DriverDO> drivers = driverService.selectByProfileId(profile.getId());
            drivers.forEach(driver -> {
                DriverConfiguration operation = new DriverConfiguration().setType(DriverConstants.Type.PROFILE).setCommand(command).setContent(profile);
                notifyDriver(driver, operation);
            });
        } catch (Exception e) {
            log.warn("Notify Driver Profile: {}", e.getMessage());
        }
    }

    @Override
    public void notifyDriverPoint(String command, PointDO point) {
        try {
            List<DriverDO> drivers = driverService.selectByProfileId(point.getProfileId());
            drivers.forEach(driver -> {
                DriverConfiguration operation = new DriverConfiguration().setType(DriverConstants.Type.POINT).setCommand(command).setContent(point);
                notifyDriver(driver, operation);
            });
        } catch (Exception e) {
            log.warn("Notify Driver Point: {}", e.getMessage());
        }
    }

    @Override
    public void notifyDriverDevice(String command, DeviceDO device) {
        try {
            DriverDO driver = driverService.selectById(device.getDriverId());
            DriverConfiguration operation = new DriverConfiguration().setType(DriverConstants.Type.DEVICE).setCommand(command).setContent(device);
            notifyDriver(driver, operation);
        } catch (Exception e) {
            log.warn("Notify Driver Device: {}", e.getMessage());
        }
    }

    @Override
    public void notifyDriverDriverInfo(String command, DriverInfo driverInfo) {
        try {
            DriverDO driver = driverService.selectByDeviceId(driverInfo.getDeviceId());
            DriverConfiguration operation = new DriverConfiguration().setType(DriverConstants.Type.DRIVER_INFO).setCommand(command).setContent(driverInfo);
            notifyDriver(driver, operation);
        } catch (Exception e) {
            log.warn("Notify Driver DriverInfo: {}", e.getMessage());
        }
    }

    @Override
    public void notifyDriverPointInfo(String command, PointInfo pointInfo) {
        try {
            DriverDO driver = driverService.selectByDeviceId(pointInfo.getDeviceId());
            DriverConfiguration operation = new DriverConfiguration().setType(DriverConstants.Type.POINT_INFO).setCommand(command).setContent(pointInfo);
            notifyDriver(driver, operation);
        } catch (Exception e) {
            log.warn("Notify Driver PointInfo: {}", e.getMessage());
        }
    }

    /**
     * notify driver
     *
     * @param driver              Driver
     * @param driverConfiguration DriverConfiguration
     */
    private void notifyDriver(DriverDO driver, DriverConfiguration driverConfiguration) {
        log.debug("Notify Driver {} : {}", driver.getServiceName(), driverConfiguration);
        driverProducer.sendDriverEvent(driverConfiguration);
    }

}
