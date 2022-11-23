package cn.iocoder.yudao.module.system.service.iot.device;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.iot.device.vo.DeviceCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.device.vo.DeviceExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.device.vo.DevicePageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.device.vo.DeviceUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.device.DeviceDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 设备 Service 接口
 *
 * @author 益莲科技
 */
public interface DeviceService {

    /**
     * 创建设备
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDevice(@Valid DeviceCreateReqVO createReqVO);

    /**
     * 更新设备
     *
     * @param updateReqVO 更新信息
     */
    void updateDevice(@Valid DeviceUpdateReqVO updateReqVO);

    /**
     * 删除设备
     *
     * @param id 编号
     */
    void deleteDevice(Long id);

    /**
     * 获得设备
     *
     * @param id 编号
     * @return 设备
     */
    DeviceDO getDevice(Long id);

    /**
     * 获得设备列表
     *
     * @param ids 编号
     * @return 设备列表
     */
    List<DeviceDO> getDeviceList(Collection<Long> ids);

    /**
     * 获得设备分页
     *
     * @param pageReqVO 分页查询
     * @return 设备分页
     */
    PageResult<DeviceDO> getDevicePage(DevicePageReqVO pageReqVO);

    /**
     * 获得设备列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 设备列表
     */
    List<DeviceDO> getDeviceList(DeviceExportReqVO exportReqVO);

    /**
     * 校验设备们是否有效。如下情况，视为无效：
     * 1. 设备编号不存在
     * 2. 设备被禁用
     *
     * @param ids 设备编号数组
     */
    void validDevices(Collection<Long> ids);

    /**
     * 根据 设备Name 和 租户Id 查询设备
     *
     * @param name     Device Name
     * @param tenantId Tenant Id
     * @return Device
     */
    DeviceDO selectByName(String name, Long tenantId);

    /**
     * 根据 驱动Id 查询该驱动下的全部设备
     *
     * @param driverId Driver Id
     * @return Device Array
     */
    List<DeviceDO> selectByDriverId(Long driverId);

    /**
     * 根据 模板Id 查询该驱动下的全部设备
     *
     * @param profileId Profile Id
     * @return Device Array
     */
    List<DeviceDO> selectByProfileId(Long profileId);

    /**
     * 根据 设备Id集 查询设备
     *
     * @param ids Device Id Set
     * @return Device Array
     */
    List<DeviceDO> selectByIds(Set<Long> ids);

    DeviceDO selectById(Long id);
}
