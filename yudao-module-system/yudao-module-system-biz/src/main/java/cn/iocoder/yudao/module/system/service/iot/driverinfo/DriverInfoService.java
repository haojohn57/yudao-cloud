package cn.iocoder.yudao.module.system.service.iot.driverinfo;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverinfo.vo.DriverInfoCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverinfo.vo.DriverInfoExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverinfo.vo.DriverInfoPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverinfo.vo.DriverInfoUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.driver.DriverInfoDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 协议配置信息 Service 接口
 *
 * @author 益莲科技
 */
public interface DriverInfoService {

    /**
     * 创建协议配置信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDriverInfo(@Valid DriverInfoCreateReqVO createReqVO);

    /**
     * 更新协议配置信息
     *
     * @param updateReqVO 更新信息
     */
    void updateDriverInfo(@Valid DriverInfoUpdateReqVO updateReqVO);

    /**
     * 删除协议配置信息
     *
     * @param id 编号
     */
    void deleteDriverInfo(Long id);

    /**
     * 获得协议配置信息
     *
     * @param id 编号
     * @return 协议配置信息
     */
    DriverInfoDO getDriverInfo(Long id);

    /**
     * 获得协议配置信息列表
     *
     * @param ids 编号
     * @return 协议配置信息列表
     */
    List<DriverInfoDO> getDriverInfoList(Collection<Long> ids);

    /**
     * 获得协议配置信息分页
     *
     * @param pageReqVO 分页查询
     * @return 协议配置信息分页
     */
    PageResult<DriverInfoDO> getDriverInfoPage(DriverInfoPageReqVO pageReqVO);

    /**
     * 获得协议配置信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 协议配置信息列表
     */
    List<DriverInfoDO> getDriverInfoList(DriverInfoExportReqVO exportReqVO);

    /**
     * 根据驱动属性配置 ID 和 设备 ID 查询
     *
     * @param driverAttributeId Driver Attribute Id
     * @param deviceId          Device Id
     * @return DriverInfo
     */
    DriverInfoDO selectByAttributeIdAndDeviceId(Long driverAttributeId, Long deviceId);

    /**
     * 根据驱动属性配置 ID 查询
     *
     * @param driverAttributeId Driver Attribute Id
     * @return DriverInfo Array
     */
    List<DriverInfoDO> selectByAttributeId(Long driverAttributeId);

    /**
     * 根据设备 ID 查询
     *
     * @param deviceId Device Id
     * @return DriverInfo Array
     */
    List<DriverInfoDO> selectByDeviceId(Long deviceId);

    DriverInfoDO selectById(Long id);
}
