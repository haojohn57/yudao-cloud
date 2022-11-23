package cn.iocoder.yudao.module.system.service.iot;

import cn.iocoder.yudao.framework.common.enums.DriverConstants;
import cn.iocoder.yudao.framework.common.exception.DuplicateException;
import cn.iocoder.yudao.framework.common.iot.bean.point.PointDetail;
import cn.iocoder.yudao.module.system.controller.admin.iot.device.vo.DeviceCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.point.vo.PointCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.profile.vo.ProfileCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.profilebind.vo.ProfileBindCreateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.device.DeviceDO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.point.PointDO;
import cn.iocoder.yudao.module.system.service.iot.device.DeviceService;
import cn.iocoder.yudao.module.system.service.iot.point.PointService;
import cn.iocoder.yudao.module.system.service.iot.profile.ProfileService;
import cn.iocoder.yudao.module.system.service.iot.profilebind.ProfileBindService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * AutoService Impl
 *
 * @author 益莲科技
 */
@Slf4j
@Service
public class AutoServiceImpl implements AutoService {

    @Resource
    private DeviceService deviceService;
    @Resource
    private ProfileService profileService;
    @Resource
    private ProfileBindService profileBindService;
    @Resource
    private PointService pointService;

    @Resource
    private NotifyService notifyService;


    @Override
    public PointDetail autoCreateDeviceAndPoint(String deviceName, String pointName, Long driverId, Long tenantId) {
        // add device
        DeviceCreateReqVO deviceCreateReqVO = new DeviceCreateReqVO();
        deviceCreateReqVO.setName(deviceName).setDriverId(driverId).setTenantId(tenantId).setDescription("auto create by driver");
        Long deviceId = null;
        try {
            deviceId = deviceService.createDevice(deviceCreateReqVO);
            DeviceDO deviceDO = deviceService.selectById(deviceId);
            // notify driver add device
            notifyService.notifyDriverDevice(DriverConstants.Device.ADD, deviceDO);
        } catch (DuplicateException duplicateException) {
            deviceService.selectByName(deviceName, tenantId);
        } catch (Exception ignored) {
        }

        // add private profile for device
        ProfileCreateReqVO createReqVO = new ProfileCreateReqVO();
        createReqVO.setName(deviceName).setShare(1).setType((int) 2).setTenantId(tenantId);
        Long profileId = null;
        try {
            profileId = profileService.createProfile(createReqVO);
        } catch (DuplicateException duplicateException) {
            profileService.selectByNameAndType(deviceName, (short) 2, tenantId);
        } catch (Exception ignored) {
        }

        // add profile bind
        if (null != deviceId && null != profileId) {
            try {
                ProfileBindCreateReqVO profileBindCreateReqVO = new ProfileBindCreateReqVO();

                profileBindCreateReqVO.setDeviceId(deviceId).setProfileId(profileId);
                profileBindService.createProfileBind(profileBindCreateReqVO);
            } catch (Exception ignored) {
            }

            // add point
            PointCreateReqVO pointCreateReqVO = new PointCreateReqVO();
            pointCreateReqVO.setName(pointName).setProfileId(profileId).setTenantId(tenantId).setDefault();
            Long pointId = null;
            try {
                pointId = pointService.createPoint(pointCreateReqVO);
                PointDO pointDO = pointService.getPoint(pointId);
                // notify driver add point
                notifyService.notifyDriverPoint(DriverConstants.Point.ADD, pointDO);
            } catch (DuplicateException duplicateException) {
                pointService.selectByNameAndProfileId(pointName, profileId);
            } catch (Exception ignored) {
            }

            return new PointDetail(deviceId, pointId);
        }
        return null;
    }
}
