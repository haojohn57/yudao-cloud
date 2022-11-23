package cn.iocoder.yudao.module.system.service.iot;

import cn.iocoder.yudao.framework.common.exception.NotFoundException;
import cn.iocoder.yudao.framework.common.iot.bean.driver.AttributeInfo;
import cn.iocoder.yudao.framework.common.iot.bean.driver.DriverMetadata;
import cn.iocoder.yudao.framework.common.iot.model.Device;
import cn.iocoder.yudao.framework.common.iot.model.DriverAttribute;
import cn.iocoder.yudao.framework.common.iot.model.Point;
import cn.iocoder.yudao.framework.common.iot.model.PointAttribute;
import cn.iocoder.yudao.module.system.convert.iot.device.DeviceConvert;
import cn.iocoder.yudao.module.system.convert.iot.driver.DriverAttributeConvert;
import cn.iocoder.yudao.module.system.convert.iot.point.PointAttributeConvert;
import cn.iocoder.yudao.module.system.convert.iot.point.PointConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.device.DeviceDO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.driver.DriverAttributeDO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.driver.DriverDO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.driver.DriverInfoDO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.point.PointAttributeDO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.point.PointInfoDO;
import cn.iocoder.yudao.module.system.service.dept.DeptService;
import cn.iocoder.yudao.module.system.service.iot.device.DeviceService;
import cn.iocoder.yudao.module.system.service.iot.driver.DriverService;
import cn.iocoder.yudao.module.system.service.iot.driverattribute.DriverAttributeService;
import cn.iocoder.yudao.module.system.service.iot.driverinfo.DriverInfoService;
import cn.iocoder.yudao.module.system.service.iot.point.PointService;
import cn.iocoder.yudao.module.system.service.iot.pointattribute.PointAttributeService;
import cn.iocoder.yudao.module.system.service.iot.pointinfo.PointInfoService;
import cn.iocoder.yudao.module.system.service.iot.profile.ProfileService;
import cn.iocoder.yudao.module.system.service.iot.profilebind.ProfileBindService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * BatchService Impl
 *
 * @author 益莲科技
 */
@Slf4j
@Service
public class BatchServiceImpl implements BatchService {

    @Resource
    private DriverService driverService;
    @Resource
    private DriverAttributeService driverAttributeService;
    @Resource
    private DriverInfoService driverInfoService;
    @Resource
    private ProfileService profileService;
    @Resource
    private ProfileBindService profileBindService;
    @Resource
    private DeptService deptService;
    @Resource
    private DeviceService deviceService;
    @Resource
    private PointService pointService;
    @Resource
    private PointAttributeService pointAttributeService;
    @Resource
    private PointInfoService pointInfoService;

    @Resource
    private NotifyService notifyService;


/**
    @Override
    @Transactional
    public void batchImport(List<BatchDriver> batchDrivers) {
        batchDrivers.forEach(batchDriver -> {
            if (StrUtil.isBlank(batchDriver.getServiceName())) {
                throw new ServiceException("Driver service name is blank");
            }
            DriverDO driver = driverService.selectByServiceName(batchDriver.getServiceName());
            if (null == driver) {
                throw new ServiceException("Driver service does not exist: " + batchDriver.getServiceName());
            }

            batchDriver.getProfiles().forEach(batchProfile -> {

                // import Profile
                Profile profile = importProfile(driver, batchProfile);

                // import Driver Info Array
                // todo bug
                importDriverInfo(driver, null, batchProfile.getDriverConfig());

                // import Point Array
                importPoint(profile, batchProfile.getPoints());

                // import Device Array
                importDevice(driver, profile, batchProfile.getGroups(), batchProfile.getPointConfig());
            });
        });
    }

    @Override
    public BatchDriver batchExport(String serviceName) {
        DriverDO driver = driverService.selectByServiceName(serviceName);

        BatchDriver batchDriver = new BatchDriver();
        batchDriver.setServiceName(serviceName);

        // export profile
        List<BatchProfile> profiles = exportProfile(driver.getId());
        if (profiles.size() > 0) {
            batchDriver.setProfiles(profiles);
        }

        return batchDriver;
    }   */

    @Override
    public DriverMetadata batchDriverMetadata(String serviceName) {
        DriverMetadata driverMetadata = new DriverMetadata();
        DriverDO driver = driverService.selectByServiceName(serviceName);
        driverMetadata.setDriverId(driver.getId()).setTenantId(driver.getTenantId());

        try {
            Map<Long, DriverAttribute> driverAttributeMap = getDriverAttributeMap(driver.getId());
            driverMetadata.setDriverAttributeMap(driverAttributeMap);

            Map<Long, PointAttributeDO> pointAttributeMapDO = getPointAttributeMap(driver.getId());
            Map<Long, PointAttribute> pointAttributeMap = PointAttributeConvert.INSTANCE.convertMap(pointAttributeMapDO);
            driverMetadata.setPointAttributeMap(pointAttributeMap);

            List<DeviceDO> devices = deviceService.selectByDriverId(driver.getId());
            Set<Long> deviceIds = devices.stream().map(DeviceDO::getId).collect(Collectors.toSet());

            Map<Long, Map<String, AttributeInfo>> driverInfoMap = getDriverInfoMap(deviceIds, driverAttributeMap);
            driverMetadata.setDriverInfoMap(driverInfoMap);

            Map<Long, DeviceDO> deviceMapDO = getDeviceMap(devices);
            Map<Long, Device> deviceMap = DeviceConvert.INSTANCE.convertMap(deviceMapDO);
            driverMetadata.setDeviceMap(deviceMap);

            Map<Long, Map<Long, Point>> profilePointMap = getProfilePointMap(deviceIds);
            driverMetadata.setProfilePointMap(profilePointMap);

            Map<Long, Map<Long, Map<String, AttributeInfo>>> devicePointInfoMap = getPointInfoMap(DeviceConvert.INSTANCE.convertList01(devices), profilePointMap, pointAttributeMap);
            driverMetadata.setPointInfoMap(devicePointInfoMap);

            return driverMetadata;
        } catch (NotFoundException ignored) {
        }

        return driverMetadata;
    }

    /**
     * 导入 Profile
     *
     * @param driver       Driver
     * @param batchProfile BatchProfile

    private Profile importProfile(Driver driver, BatchProfile batchProfile) {
        if (StrUtil.isBlank(batchProfile.getName())) {
            throw new ServiceException("Profile name is blank");
        }

        Profile profile;
        try {
            profile = profileService.selectByNameAndType(batchProfile.getName(), batchProfile.getType(), driver.getTenantId());
            profile.setShare(batchProfile.getShare());
            profile.setDescription("批量导入：更新操作");
            profile = profileService.update(profile);
            notifyService.notifyDriverProfile(CommonConstant.Driver.Profile.UPDATE, profile);
        } catch (NotFoundException notFoundException) {
            profile = new Profile(batchProfile.getName(), batchProfile.getShare(), driver.getId(), driver.getTenantId());
            profile.setDescription("批量导入：新增操作");
            profile = profileService.add(profile);
            notifyService.notifyDriverProfile(CommonConstant.Driver.Profile.ADD, profile);
        }

        return profile;
    }  */

    /**
     * 导入 Driver Info 列表
     *
     * @param driver       Driver
     * @param device       Device
     * @param driverConfig Driver Config

    private void importDriverInfo(Driver driver, Device device, final Map<String, String> driverConfig) {
        List<String> driverInfoList = new ArrayList<>(16);
        if (null == driverConfig) {
            return;
        }

        driverConfig.forEach((name, value) -> {
            DriverAttributeDO driverAttribute = driverAttributeService.selectByNameAndDriverId(name, driver.getId());
            if (driverInfoList.contains(name)) {
                throw new ServiceException("Repeatedly driver info: " + name);
            }
            driverInfoList.add(name);

            try {
                DriverInfoDO driverInfo = driverInfoService.selectByAttributeIdAndDeviceId(driverAttribute.getId(), device.getId());
                driverInfo.setDescription("批量导入：更新操作");
                driverInfo = driverInfoService.update(driverInfo.setValue(value));
                notifyService.notifyDriverDriverInfo(CommonConstant.Driver.DriverInfo.UPDATE, driverInfo);
            } catch (NotFoundException notFoundException) {
                DriverInfo driverInfo = new DriverInfo(driverAttribute.getId(), value, device.getId());
                driverInfo.setDescription("批量导入：新增操作");
                driverInfo = driverInfoService.add(driverInfo);
                notifyService.notifyDriverDriverInfo(CommonConstant.Driver.DriverInfo.ADD, driverInfo);
            }
        });
    }   */

    /**
     * 导入 Point 列表
     *
     * @param profile Profile
     * @param points  Point Array

    private void importPoint(Profile profile, List<BatchPoint> points) {
        points.forEach(importPoint -> {
            // If point does not exist, add a new point, otherwise point will be updated
            try {
                PointDO point = pointService.selectByNameAndProfileId(importPoint.getName(), profile.getId());
                point
                        .setName(importPoint.getName())
                        .setType(importPoint.getType())
                        .setRw(importPoint.getRw())
                        .setBase(importPoint.getBase())
                        .setMinimum(importPoint.getMinimum())
                        .setMaximum(importPoint.getMaximum())
                        .setMultiple(importPoint.getMultiple())
                        .setAccrue(importPoint.getAccrue())
                        .setFormat(importPoint.getFormat())
                        .setUnit(importPoint.getUnit());
                point.setDescription("批量导入：更新操作");
                pointService.update(point);
                notifyService.notifyDriverPoint(CommonConstant.Driver.Point.UPDATE, point);
            } catch (NotFoundException notFoundException) {
                Point point = new Point(
                        importPoint.getName(),
                        importPoint.getType(),
                        importPoint.getRw(),
                        importPoint.getBase(),
                        importPoint.getMinimum(),
                        importPoint.getMaximum(),
                        importPoint.getMultiple(),
                        importPoint.getAccrue(),
                        importPoint.getFormat(),
                        importPoint.getUnit(),
                        profile.getId(),
                        profile.getTenantId()
                );
                point.setDescription("批量导入：新增操作");
                point = pointService.add(point);
                notifyService.notifyDriverPoint(CommonConstant.Driver.Point.ADD, point);
            }
        });
    }     */

    /**
     * 导入 Device 列表
     *
     * @param driver  Driver
     * @param profile Profile
     * @param groups  Device Group

    private void importDevice(Driver driver, Profile profile, List<BatchGroup> groups, final Map<String, Map<String, String>> pointConfig) {
        groups.forEach(importGroup -> {
            // If group does not exist, add a new group
            Group groupTemp;
            try {
                groupTemp = groupService.selectByName(importGroup.getName(), driver.getTenantId());
            } catch (NotFoundException notFoundException) {
                groupTemp = new Group(importGroup.getName(), driver.getTenantId());
                groupTemp = groupService.add(groupTemp);
            }

            final Group group = groupTemp;
            importGroup.getDevices().forEach(batchDevice -> {
                Device device;
                try {
                    // todo bug
                    //device = deviceService.selectDeviceByNameAndGroupId(batchDevice.getName(), group.getId());
                    device = null;
                    device.setMulti(batchDevice.getMulti());
                    device.setDescription("批量导入：更新操作");
                    deviceService.update(device);
                    notifyService.notifyDriverDevice(CommonConstant.Driver.Device.UPDATE, device);
                } catch (NotFoundException notFoundException) {
                    device = new Device(batchDevice.getName(), profile.getId(), group.getId());
                    if (batchDevice.getMulti()) {
                        device.setMulti(true);
                    }
                    device.setDescription("批量导入：新增操作");
                    device = deviceService.add(device);
                    notifyService.notifyDriverDevice(CommonConstant.Driver.Device.ADD, device);
                }

                // Upsert Point Info
                if (profile.getShare()) {
                    importPointInfo(driver, profile, device, pointConfig);
                } else {
                    importPointInfo(driver, profile, device, batchDevice.getPointConfig());
                }
            });
        });
    }  */

    /**
     * 导入 Point Info 列表
     *
     * @param driver      Driver
     * @param profile     Profile
     * @param device      Device
     * @param pointConfig Point Config Map

    private void importPointInfo(Driver driver, Profile profile, Device device, final Map<String, Map<String, String>> pointConfig) {
        pointConfig.forEach((pointName, pointConfigMap) -> {
            List<String> pointInfoList = new ArrayList<>(16);
            pointConfigMap.forEach((name, value) -> {
                PointAttributeDO pointAttribute = pointAttributeService.selectByNameAndDriverId(name, driver.getId());
                if (pointInfoList.contains(name)) {
                    throw new ServiceException("Repeatedly point info: " + name);
                }
                pointInfoList.add(name);

                PointDO point = pointService.selectByNameAndProfileId(pointName, profile.getId());

                // If point info does not exist, add a new point info, otherwise point info will be updated
                try {
                    PointInfoDO pointInfo = pointInfoService.selectByAttributeIdAndDeviceIdAndPointId(pointAttribute.getId(), device.getId(), point.getId());
                    pointInfo.setDescription("批量导入：更新操作");
                    pointInfo = pointInfoService.update(pointInfo.setValue(value));
                    notifyService.notifyDriverPointInfo(CommonConstant.Driver.PointInfo.UPDATE, pointInfo);
                } catch (NotFoundException notFoundException) {
                    PointInfo pointInfo = new PointInfo(pointAttribute.getId(), value, device.getId(), point.getId());
                    pointInfo.setDescription("批量导入：新增操作");
                    pointInfo = pointInfoService.add(pointInfo);
                    notifyService.notifyDriverPointInfo(CommonConstant.Driver.PointInfo.ADD, pointInfo);
                }
            });
        });
    }   */

    /**
     * 导出 Profile
     *
     * @param driverId Driver Id
     * @return BatchProfile Array

    private List<BatchProfile> exportProfile(String driverId) {
        List<BatchProfile> batchProfiles = new ArrayList<>(16);

        /*profileService.selectByDriverId(driverId)
                .forEach(profile -> {
                    BatchProfile batchProfile = new BatchProfile();
                    batchProfile.setName(profile.getName());

                    // todo export share
                    batchProfile.setShare(false);

                    // export driver config
                    Map<String, String> driverConfig = exportDriverConfig(driverId, profile.getId());
                    if (driverConfig.size() > 0) {
                        batchProfile.setDriverConfig(driverConfig);
                    }

                    // export point
                    List<BatchPoint> points = exportPoint(profile.getId());
                    if (points.size() > 0) {
                        batchProfile.setPoints(points);
                    }

                    // export group
                    List<BatchGroup> groups = exportGroup(
                            profile.getId(),
                            pointAttributeService.selectByDriverId(driverId)
                    );
                    if (groups.size() > 0) {
                        batchProfile.setGroups(groups);
                    }

                    batchProfiles.add(batchProfile);
                });//

        return batchProfiles;
    }   */

    /**
     * 导出 DriverConfig
     *
     * @param driverId Driver Id
     * @param deviceId Device Id
     * @return Map<String, String> DriverConfig

    private Map<String, String> exportDriverConfig(Long driverId, Long deviceId) {
        Map<String, String> driverConfigMap = new HashMap<>(16);

        driverAttributeService.selectByDriverId(driverId)
                .forEach(driverAttribute -> {
                    DriverInfoDO driverInfo = driverInfoService
                            .selectByAttributeIdAndDeviceId(
                                    driverAttribute.getId(),
                                    deviceId
                            );
                    driverConfigMap.put(driverAttribute.getName(), driverInfo.getValue());
                });

        return driverConfigMap;
    }  */

    /**
     * 导出 Point
     *
     * @param profileId Profile Id
     * @return BatchPoint Array

    private List<BatchPoint> exportPoint(Long profileId) {
        List<BatchPoint> batchPoints = new ArrayList<>(16);

        pointService.selectByProfileId(profileId)
                .forEach(point -> {
                    BatchPoint batchPoint = new BatchPoint();
                    BeanUtils.copyProperties(point, batchPoint);
                    batchPoints.add(batchPoint);
                });

        return batchPoints;
    }  */

    /**
     * 导出 Group
     *
     * @param profileId Profile Id
     * @return BatchGroup Array

    private List<BatchGroup> exportGroup(String profileId, List<PointAttribute> pointAttributes) {
        List<BatchGroup> batchGroups = new ArrayList<>(16);

        List<Device> devices = deviceService.selectByDriverId(profileId);
        List<Point> points = pointService.selectByProfileId(profileId);

        devices.stream().map(Device::getGroupId).distinct()
                .forEach(groupId -> {
                    Group group = groupService.selectById(groupId);
                    BatchGroup batchGroup = new BatchGroup();
                    batchGroup.setName(group.getName());

                    // export device
                    List<BatchDevice> batchDevices = exportDevice(
                            groupId,
                            devices,
                            points,
                            pointAttributes
                    );

                    if (batchDevices.size() > 0) {
                        batchGroup.setDevices(batchDevices);
                    }
                    batchGroups.add(batchGroup);
                });

        return batchGroups;
    } */

    /**
     * Export device array
     *
     * @param groupId         Group Id
     * @param devices         Device Array
     * @param points          Point Array
     * @param pointAttributes PointAttribute Array
     * @return BatchDevice Array

    private List<BatchDevice> exportDevice(String groupId, List<Device> devices, List<Point> points, List<PointAttribute> pointAttributes) {
        List<BatchDevice> batchDevices = new ArrayList<>(16);

        devices.stream().filter(device -> device.getGroupId().equals(groupId))
                .forEach(device -> {
                    // export point config
                    Map<String, Map<String, String>> pointConfigMap = new HashMap<>(16);
                    points.forEach(point -> {
                        Map<String, String> configMap = new HashMap<>(8);
                        pointAttributes.forEach(pointAttribute -> {
                            try {
                                PointInfoDO pointInfo = pointInfoService
                                        .selectByAttributeIdAndDeviceIdAndPointId(
                                                pointAttribute.getId(),
                                                device.getId(),
                                                point.getId()
                                        );
                                configMap.put(pointAttribute.getName(), pointInfo.getValue());
                            } catch (NotFoundException ignored) {
                            }
                        });

                        if (configMap.size() > 0) {
                            pointConfigMap.put(point.getName(), configMap);
                        }
                    });

                    BatchDevice batchDevice = new BatchDevice();
                    batchDevice.setName(device.getName());
                    batchDevice.setMulti(device.getMulti());

                    if (pointConfigMap.size() > 0) {
                        batchDevice.setPointConfig(pointConfigMap);
                    }
                    batchDevices.add(batchDevice);
                });

        return batchDevices;
    } */

    /**
     * Get driver attribute map
     *
     * @param driverId Driver Id
     * @return map(driverAttributeId, driverAttribute)
     */
    public Map<Long, DriverAttribute> getDriverAttributeMap(Long driverId) {
        Map<Long, DriverAttribute> driverAttributeMap = new ConcurrentHashMap<>(16);
        try {
            List<DriverAttributeDO> driverAttributesDO = driverAttributeService.selectByDriverId(driverId);
            driverAttributesDO.forEach(driverAttributeDO -> driverAttributeMap.put(driverAttributeDO.getId(), DriverAttributeConvert.INSTANCE.convert3(driverAttributeDO)));
        } catch (NotFoundException ignored) {
        }
        return driverAttributeMap;
    }

    /**
     * Get point attribute map
     *
     * @param driverId Driver Id
     * @return map(pointAttributeId, pointAttribute)
     */
    public Map<Long, PointAttributeDO> getPointAttributeMap(Long driverId) {
        Map<Long, PointAttributeDO> pointAttributeMap = new ConcurrentHashMap<>(16);
        try {
            List<PointAttributeDO> pointAttributes = pointAttributeService.selectByDriverId(driverId);
            pointAttributes.forEach(pointAttribute -> pointAttributeMap.put(pointAttribute.getId(), pointAttribute));
        } catch (NotFoundException ignored) {
        }
        return pointAttributeMap;
    }

    /**
     * Get driver info map
     *
     * @param deviceList         Device Set
     * @param driverAttributeMap Driver Attribute Map
     * @return map(deviceId ( driverAttribute.name, ( drverInfo.value, driverAttribute.type)))
     */
    public Map<Long, Map<String, AttributeInfo>> getDriverInfoMap(Set<Long> deviceList, Map<Long, DriverAttribute> driverAttributeMap) {
        Map<Long, Map<String, AttributeInfo>> driverInfoMap = new ConcurrentHashMap<>(16);
        deviceList.forEach(deviceId -> {
            Map<String, AttributeInfo> infoMap = getDriverInfoMap(deviceId, driverAttributeMap);
            if (infoMap.size() > 0) {
                driverInfoMap.put(deviceId, infoMap);
            }
        });
        return driverInfoMap;
    }

    /**
     * Get driver info map
     *
     * @param deviceId           Device Id
     * @param driverAttributeMap Driver Attribute Map
     * @return map(attributeName, attributeInfo ( value, type))
     */
    public Map<String, AttributeInfo> getDriverInfoMap(Long deviceId, Map<Long, DriverAttribute> driverAttributeMap) {
        Map<String, AttributeInfo> attributeInfoMap = new ConcurrentHashMap<>(16);
        try {
            List<DriverInfoDO> driverInfos = driverInfoService.selectByDeviceId(deviceId);
            driverInfos.forEach(driverInfo -> {
                DriverAttribute attribute = driverAttributeMap.get(driverInfo.getDriverAttributeId());
                attributeInfoMap.put(attribute.getName(), new AttributeInfo(driverInfo.getValue(), attribute.getType()));
            });
        } catch (NotFoundException ignored) {
        }
        return attributeInfoMap;
    }

    /**
     * Get point info map
     *
     * @param devices           Device Array
     * @param profilePointMap   Profile Point Map
     * @param pointAttributeMap Point Attribute Map
     * @return map(deviceId ( pointId, attribute ( attributeName, attributeInfo ( value, type))))
     */
    public Map<Long, Map<Long, Map<String, AttributeInfo>>> getPointInfoMap(List<Device> devices, Map<Long, Map<Long, Point>> profilePointMap, Map<Long, PointAttribute> pointAttributeMap) {
        Map<Long, Map<Long, Map<String, AttributeInfo>>> devicePointInfoMap = new ConcurrentHashMap<>(16);
        devices.forEach(device -> {
            Map<Long, Map<String, AttributeInfo>> infoMap = getPointInfoMap(device, profilePointMap, pointAttributeMap);
            if (infoMap.size() > 0) {
                devicePointInfoMap.put(device.getId(), infoMap);
            }
        });
        return devicePointInfoMap;
    }

    /**
     * Get point info map
     *
     * @param device            Device
     * @param profilePointMap   Profile Point Map
     * @param pointAttributeMap Point Attribute Map
     * @return map(pointId, attribute ( attributeName, attributeInfo ( value, type)))
     */
    public Map<Long, Map<String, AttributeInfo>> getPointInfoMap(Device device, Map<Long, Map<Long, Point>> profilePointMap, Map<Long, PointAttribute> pointAttributeMap) {
        Map<Long, Map<String, AttributeInfo>> attributeInfoMap = new ConcurrentHashMap<>(16);
        device.getProfileIds().forEach(profileId -> profilePointMap.get(profileId).keySet()
                .forEach(pointId -> {
                    try {
                        List<PointInfoDO> pointInfos = pointInfoService.selectByDeviceIdAndPointId(device.getId(), pointId);
                        Map<String, AttributeInfo> infoMap = new ConcurrentHashMap<>(16);
                        pointInfos.forEach(pointInfo -> {
                            PointAttribute attribute = pointAttributeMap.get(pointInfo.getPointAttributeId());
                            infoMap.put(attribute.getName(), new AttributeInfo(pointInfo.getValue(), attribute.getType()));
                        });

                        if (infoMap.size() > 0) {
                            attributeInfoMap.put(pointId, infoMap);
                        }
                    } catch (NotFoundException ignored) {
                    }
                }));
        return attributeInfoMap;
    }

    /**
     * Get device map
     *
     * @param devices Device Array
     * @return map(pointId, point)
     */
    public Map<Long, DeviceDO> getDeviceMap(List<DeviceDO> devices) {
        Map<Long, DeviceDO> deviceMap = new ConcurrentHashMap<>(16);
        devices.forEach(device -> deviceMap.put(device.getId(), device));
        return deviceMap;
    }

    /**
     * Get profile  map
     *
     * @param deviceIds Device Id Set
     * @return map(profileId ( pointId, point))
     */
    public Map<Long, Map<Long, Point>> getProfilePointMap(Set<Long> deviceIds) {
        Map<Long, Map<Long, Point>> profilePointMap = new ConcurrentHashMap<>(16);
        deviceIds.forEach(deviceId -> {
            Set<Long> profileIds = profileBindService.selectProfileIdByDeviceId(deviceId);
            profileIds.forEach(profileId -> profilePointMap.put(profileId, getPointMap(profileId)));
        });
        return profilePointMap;
    }

    /**
     * Get point map
     *
     * @param profileId Profile Id
     * @return map(pointId, point)
     */
    public Map<Long, Point> getPointMap(Long profileId) {
        Map<Long, Point> pointMap = new ConcurrentHashMap<>(16);
        try {
            pointService.selectByProfileId(profileId).forEach(point -> pointMap.put(point.getId(), PointConvert.INSTANCE.convert3(point)));
        } catch (NotFoundException ignored) {
        }
        return pointMap;
    }

}
