package cn.iocoder.yudao.framework.driversdk.core.service;

import cn.iocoder.yudao.framework.common.iot.model.*;

/**
 * @author 益莲科技
 */
public interface DriverMetadataService {

    /**
     * 初始化 SDK
     */
    void initial();

    /**
     * 向 DeviceDriver 中添加模板
     *
     * @param profile Profile
     */
    void upsertProfile(Profile profile);

    /**
     * 删除 DeviceDriver 中模板
     *
     * @param id Id
     */
    void deleteProfile(Long id);

    /**
     * 向 DeviceDriver 中添加设备
     *
     * @param device Device
     */
    void upsertDevice(Device device);

    /**
     * 删除 DeviceDriver 中设备
     *
     * @param id Id
     */
    void deleteDevice(Long id);

    /**
     * 向 DeviceDriver 中添加位号
     *
     * @param point Point
     */
    void upsertPoint(Point point);

    /**
     * 删除 DeviceDriver 中位号
     *
     * @param profileId Profile Id
     * @param id        Id
     */
    void deletePoint(Long profileId, Long id);

    /**
     * 向 DeviceDriver 中添加驱动配置信息
     *
     * @param driverInfo DriverInfo
     */
    void upsertDriverInfo(DriverInfo driverInfo);

    /**
     * 删除 DeviceDriver 中添加驱动配置信息
     *
     * @param deviceId    Device Id
     * @param attributeId Attribute Id
     */
    void deleteDriverInfo(Long deviceId, Long attributeId);

    /**
     * 向 DeviceDriver 中添加位号配置信息
     *
     * @param pointInfo PointInfo
     */
    void upsertPointInfo(PointInfo pointInfo);

    /**
     * 删除 DeviceDriver 中添加位号配置信息
     *
     * @param deviceId    Device Id
     * @param pointId     Point Id
     * @param attributeId Attribute Id
     */
    void deletePointInfo(Long deviceId, Long pointId, Long attributeId);
}
