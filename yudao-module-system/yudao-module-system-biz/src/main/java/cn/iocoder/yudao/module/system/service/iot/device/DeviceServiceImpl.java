package cn.iocoder.yudao.module.system.service.iot.device;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.framework.common.enums.DriverConstants;
import cn.iocoder.yudao.framework.common.exception.NotFoundException;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.iot.device.vo.DeviceCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.device.vo.DeviceExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.device.vo.DevicePageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.device.vo.DeviceUpdateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.profilebind.vo.ProfileBindCreateReqVO;
import cn.iocoder.yudao.module.system.convert.iot.device.DeviceConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.device.DeviceDO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.point.PointDO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.profile.ProfileDO;
import cn.iocoder.yudao.module.system.dal.mysql.iot.device.DeviceMapper;
import cn.iocoder.yudao.module.system.service.iot.NotifyService;
import cn.iocoder.yudao.module.system.service.iot.point.PointService;
import cn.iocoder.yudao.module.system.service.iot.profile.ProfileService;
import cn.iocoder.yudao.module.system.service.iot.profilebind.ProfileBindService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertMap;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.DEVICE_NOT_ENABLE;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.DEVICE_NOT_EXISTS;

/**
 * 设备 Service 实现类
 *
 * @author 益莲科技
 */
@Service
@Validated
public class DeviceServiceImpl implements DeviceService {

    @Resource
    private DeviceMapper deviceMapper;
    @Resource
    ProfileBindService profileBindService;

    @Resource
    private ProfileService profileService;

    @Resource
    private PointService pointService;
    @Resource
    private NotifyService notifyService;

    @Override
    public Long createDevice(DeviceCreateReqVO createReqVO) {
        // 插入
        DeviceDO device = DeviceConvert.INSTANCE.convert(createReqVO);
        deviceMapper.insert(device);
        // 返回
        return device.getId();
    }

    @Override
    public void updateDevice(DeviceUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateDeviceExists(updateReqVO.getId());
        // 更新
        DeviceDO updateObj = DeviceConvert.INSTANCE.convert(updateReqVO);
        deviceMapper.updateById(updateObj);
    }

    @Override
    public void deleteDevice(Long id) {
        // 校验存在
        this.validateDeviceExists(id);
        // 删除
        deviceMapper.deleteById(id);
    }

    private void validateDeviceExists(Long id) {
        if (deviceMapper.selectById(id) == null) {
            throw exception(DEVICE_NOT_EXISTS);
        }
    }

    @Override
    public DeviceDO getDevice(Long id) {
        return deviceMapper.selectById(id);
    }

    @Override
    public List<DeviceDO> getDeviceList(Collection<Long> ids) {
        return deviceMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DeviceDO> getDevicePage(DevicePageReqVO pageReqVO) {
        return deviceMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DeviceDO> getDeviceList(DeviceExportReqVO exportReqVO) {
        return deviceMapper.selectList(exportReqVO);
    }

    @Override
    public void validDevices(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 获得设备信息
        List<DeviceDO> devices = deviceMapper.selectBatchIds(ids);
        Map<Long, DeviceDO> deviceMap = convertMap(devices, DeviceDO::getId);
        // 校验
        ids.forEach(id -> {
            DeviceDO device = deviceMap.get(id);
            if (device == null) {
                throw exception(DEVICE_NOT_EXISTS);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(device.getStatus())) {
                throw exception(DEVICE_NOT_ENABLE, device.getName());
            }
        });
    }

    @Override
    public List<DeviceDO> selectByProfileId(Long profileId) {
        return selectByIds(profileBindService.selectDeviceIdByProfileId(profileId));
    }

    @Override
    public DeviceDO selectById(Long id) {
        DeviceDO device = deviceMapper.selectById(id);
        if (null == device) {
            throw new NotFoundException("The device does not exist");
        }
        return device.setProfileIds(profileBindService.selectProfileIdByDeviceId(id));
    }

    @Override
    public DeviceDO selectByName(String name, Long tenantId) {

        DeviceDO device = deviceMapper.selectOne(
                new LambdaQueryWrapperX<DeviceDO>()
                        .eq(DeviceDO::getName, name)
                        .eq(DeviceDO::getTenantId, tenantId));
        if (null == device) {
            throw new NotFoundException("The device does not exist");
        }
        return device.setProfileIds(profileBindService.selectProfileIdByDeviceId(device.getId()));
    }

    @Override
    public List<DeviceDO> selectByDriverId(Long driverId) {
        DeviceExportReqVO deviceReqVO = new DeviceExportReqVO();
        deviceReqVO.setDriverId(driverId);
        List<DeviceDO> devices = deviceMapper.selectList(deviceReqVO);
        if (null == devices || devices.size() < 1) {
            throw new NotFoundException("The devices does not exist");
        }
        devices.forEach(device -> device.setProfileIds(profileBindService.selectProfileIdByDeviceId(device.getId())));
        return devices;
    }

    @Override
    public List<DeviceDO> selectByIds(Set<Long> ids) {
        List<DeviceDO> devices = deviceMapper.selectBatchIds(ids);
        if (CollectionUtil.isEmpty(devices)) {
            throw new NotFoundException("The devices does not exist");
        }
        devices.forEach(device -> device.setProfileIds(profileBindService.selectProfileIdByDeviceId(device.getId())));
        return devices;
    }

    private void addProfileBind(Long deviceId, Set<Long> profileIds) {
        if (null != profileIds) {
            profileIds.forEach(profileId -> {
                ProfileBindCreateReqVO createReqVO = new ProfileBindCreateReqVO();
                createReqVO.setDeviceId(deviceId);
                ProfileDO profile = profileService.selectById(profileId);
                createReqVO.setProfileId(profile.getId());
                profileBindService.createProfileBind(createReqVO);

                // Notify Driver Profile

                notifyService.notifyDriverProfile(DriverConstants.Profile.ADD, profile);

                // Notify Driver Point
                try {
                    List<PointDO> points = pointService.selectByProfileId(profile.getId());
                    points.forEach(point -> notifyService.notifyDriverPoint(DriverConstants.Point.ADD, point));
                } catch (Exception ignored) {
                }
            });
        }
    }
}
