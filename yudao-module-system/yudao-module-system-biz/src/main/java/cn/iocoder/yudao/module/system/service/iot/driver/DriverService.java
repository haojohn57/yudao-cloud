package cn.iocoder.yudao.module.system.service.iot.driver;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.iot.driver.vo.DriverCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driver.vo.DriverExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driver.vo.DriverPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driver.vo.DriverUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.driver.DriverDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 协议驱动 Service 接口
 *
 * @author 益莲科技
 */
public interface DriverService {

    /**
     * 创建协议驱动
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDriver(@Valid DriverCreateReqVO createReqVO);

    /**
     * 更新协议驱动
     *
     * @param updateReqVO 更新信息
     */
    void updateDriver(@Valid DriverUpdateReqVO updateReqVO);

    /**
     * 删除协议驱动
     *
     * @param id 编号
     */
    void deleteDriver(Long id);

    /**
     * 获得协议驱动
     *
     * @param id 编号
     * @return 协议驱动
     */
    DriverDO getDriver(Long id);

    /**
     * 获得协议驱动列表
     *
     * @param ids 编号
     * @return 协议驱动列表
     */
    List<DriverDO> getDriverList(Collection<Long> ids);

    /**
     * 获得协议驱动分页
     *
     * @param pageReqVO 分页查询
     * @return 协议驱动分页
     */
    PageResult<DriverDO> getDriverPage(DriverPageReqVO pageReqVO);

    /**
     * 获得协议驱动列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 协议驱动列表
     */
    List<DriverDO> getDriverList(DriverExportReqVO exportReqVO);

    DriverDO selectById(Long driverId);
    /**
     * 根据 驱动ServiceName 查询 驱动
     *
     * @param serviceName Driver Service Name
     * @return Driver
     */
    DriverDO selectByServiceName(String serviceName);

    /**
     * 根据 驱动 Host & Port 查询 驱动
     *
     * @param type Driver Type
     * @param host Driver Service Host
     * @param port Driver Service Port
     * @return Driver
     */
    DriverDO selectByHostPort(String type, String host, Integer port, Long tenantId);

    /**
     * 根据 驱动Id 查询 驱动
     *
     * @param deviceId Device Id
     * @return Driver
     */
    DriverDO selectByDeviceId(Long deviceId);

    /**
     * 根据 驱动Id集 查询 驱动集
     *
     * @param ids Driver Id Array
     * @return Driver Array
     */
    List<DriverDO> selectByIds(Set<Long> ids);

    /**
     * 根据 模版Id 查询 驱动集
     *
     * @param profileId Profile Id
     * @return Driver Array
     */
    List<DriverDO> selectByProfileId(Long profileId);
}
