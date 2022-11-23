package cn.iocoder.yudao.framework.driversdk.core.service.mq.consumer;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.enums.DriverConstants;
import cn.iocoder.yudao.framework.common.iot.bean.driver.DriverConfiguration;
import cn.iocoder.yudao.framework.common.iot.bean.driver.DriverMetadata;
import cn.iocoder.yudao.framework.common.iot.message.DriverConfigMessage;
import cn.iocoder.yudao.framework.common.iot.model.*;
import cn.iocoder.yudao.framework.driversdk.core.bean.driver.DriverContext;
import cn.iocoder.yudao.framework.driversdk.core.service.DriverMetadataService;
import cn.iocoder.yudao.framework.driversdk.core.service.DriverServiceSdk;
import cn.iocoder.yudao.framework.driversdk.core.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 接收驱动发送过来的数据
 *
 * @author 益莲科技
 */
@Slf4j
@Component
public class DriverMetadataReceiver {

    @Resource
    private DriverContext driverContext;
    @Resource
    private DriverServiceSdk driverSdkService;
    @Resource
    private DriverMetadataService driverMetadataService;

    @StreamListener("driver-config-input-01")
    public void onMessage(DriverConfigMessage message) {
        try {
            DriverConfiguration driverConfiguration = message.getDriverConfiguration();
            if (null == driverConfiguration || StrUtil.isEmpty(driverConfiguration.getType()) || StrUtil.isEmpty(driverConfiguration.getCommand())) {
                log.error("Invalid driver configuration {}", driverConfiguration);
                return;
            }

            switch (driverConfiguration.getType()) {
                case DriverConstants.Type.DRIVER -> configurationDriver(driverConfiguration);
                case DriverConstants.Type.PROFILE -> configurationProfile(driverConfiguration);
                case DriverConstants.Type.DEVICE -> configurationDevice(driverConfiguration);
                case DriverConstants.Type.POINT -> configurationPoint(driverConfiguration);
                case DriverConstants.Type.DRIVER_INFO -> configurationDriverInfo(driverConfiguration);
                case DriverConstants.Type.POINT_INFO -> configurationPointInfo(driverConfiguration);
                default -> {
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 配置 driver
     *
     * @param driverConfiguration DriverConfiguration
     */
    private void configurationDriver(DriverConfiguration driverConfiguration) {
        if (!DriverConstants.Response.OK.equals(driverConfiguration.getResponse())) {
            driverSdkService.close("The driver initialization failed: {}", driverConfiguration.getResponse());
        }

        switch (driverConfiguration.getCommand()) {
            case DriverConstants.Event.DRIVER_HANDSHAKE_BACK ->
                    driverContext.setDriverStatus(DriverConstants.Status.REGISTERING);
            case DriverConstants.Event.DRIVER_REGISTER_BACK ->
                    driverContext.setDriverStatus(DriverConstants.Status.ONLINE);
            case DriverConstants.Event.DRIVER_METADATA_SYNC_BACK -> {
                DriverMetadata driverMetadata = Convert.convert(DriverMetadata.class, driverConfiguration.getContent());
                log.debug("Initialization driver metadata: {}", JsonUtil.toPrettyJsonString(driverMetadata));
                driverContext.setDriverMetadata(driverMetadata);
            }
            default -> {
            }
        }
    }

    /**
     * 配置 driver profile
     *
     * @param driverConfiguration DriverConfiguration
     */
    private void configurationProfile(DriverConfiguration driverConfiguration) {
        Profile profile = Convert.convert(Profile.class, driverConfiguration.getContent());
        if (DriverConstants.Profile.ADD.equals(driverConfiguration.getCommand()) || DriverConstants.Profile.UPDATE.equals(driverConfiguration.getCommand())) {
            log.info("Upsert profile \n{}", JsonUtil.toJsonString(profile));
            driverMetadataService.upsertProfile(profile);
        } else if (DriverConstants.Profile.DELETE.equals(driverConfiguration.getCommand())) {
            log.info("Delete profile {}", profile.getName());
            driverMetadataService.deleteProfile(profile.getId());
        }
    }

    /**
     * 配置 driver device
     *
     * @param driverConfiguration DriverConfiguration
     */
    private void configurationDevice(DriverConfiguration driverConfiguration) {
        Device device = Convert.convert(Device.class, driverConfiguration.getContent());
        if (DriverConstants.Device.ADD.equals(driverConfiguration.getCommand()) || DriverConstants.Device.UPDATE.equals(driverConfiguration.getCommand())) {
            log.info("Upsert device \n{}", JsonUtil.toJsonString(device));
            driverMetadataService.upsertDevice(device);
        } else if (DriverConstants.Device.DELETE.equals(driverConfiguration.getCommand())) {
            log.info("Delete device {}", device.getName());
            driverMetadataService.deleteDevice(device.getId());
        }
    }

    /**
     * 配置 driver point
     *
     * @param driverConfiguration DriverConfiguration
     */
    private void configurationPoint(DriverConfiguration driverConfiguration) {
        Point point = Convert.convert(Point.class, driverConfiguration.getContent());
        if (DriverConstants.Point.ADD.equals(driverConfiguration.getCommand()) || DriverConstants.Point.UPDATE.equals(driverConfiguration.getCommand())) {
            log.info("Upsert point \n{}", JsonUtil.toJsonString(point));
            driverMetadataService.upsertPoint(point);
        } else if (DriverConstants.Point.DELETE.equals(driverConfiguration.getCommand())) {
            log.info("Delete point {}", point.getName());
            driverMetadataService.deletePoint(point.getProfileId(), point.getId());
        }
    }

    /**
     * 配置 driver info
     *
     * @param driverConfiguration DriverConfiguration
     */
    private void configurationDriverInfo(DriverConfiguration driverConfiguration) {
        DriverInfo driverInfo = Convert.convert(DriverInfo.class, driverConfiguration.getContent());
        if (DriverConstants.DriverInfo.ADD.equals(driverConfiguration.getCommand()) || DriverConstants.DriverInfo.UPDATE.equals(driverConfiguration.getCommand())) {
            log.info("Upsert driver info \n{}", JsonUtil.toJsonString(driverInfo));
            driverMetadataService.upsertDriverInfo(driverInfo);
        } else if (DriverConstants.DriverInfo.DELETE.equals(driverConfiguration.getCommand())) {
            log.info("Delete driver info {}", driverInfo);
            driverMetadataService.deleteDriverInfo(driverInfo.getDeviceId(), driverInfo.getDriverAttributeId());
        }
    }

    /**
     * 配置 driver point info
     *
     * @param driverConfiguration DriverConfiguration
     */
    private void configurationPointInfo(DriverConfiguration driverConfiguration) {
        PointInfo pointInfo = Convert.convert(PointInfo.class, driverConfiguration.getContent());
        if (DriverConstants.PointInfo.ADD.equals(driverConfiguration.getCommand()) || DriverConstants.PointInfo.UPDATE.equals(driverConfiguration.getCommand())) {
            log.info("Upsert point info \n{}", JsonUtil.toJsonString(pointInfo));
            driverMetadataService.upsertPointInfo(pointInfo);
        } else if (DriverConstants.PointInfo.DELETE.equals(driverConfiguration.getCommand())) {
            log.info("Delete point info {}", pointInfo);
            driverMetadataService.deletePointInfo(pointInfo.getPointId(), pointInfo.getId(), pointInfo.getPointAttributeId());
        }
    }

}
