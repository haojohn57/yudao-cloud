package cn.iocoder.yudao.module.system.service.iot.driverattribute;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverattribute.vo.DriverAttributeCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverattribute.vo.DriverAttributeExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverattribute.vo.DriverAttributePageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverattribute.vo.DriverAttributeUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.driver.DriverAttributeDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 连接配置信息 Service 接口
 *
 * @author 益莲科技
 */
public interface DriverAttributeService {

    /**
     * 创建连接配置信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDriverAttribute(@Valid DriverAttributeCreateReqVO createReqVO);

    /**
     * 更新连接配置信息
     *
     * @param updateReqVO 更新信息
     */
    void updateDriverAttribute(@Valid DriverAttributeUpdateReqVO updateReqVO);

    /**
     * 删除连接配置信息
     *
     * @param id 编号
     */
    void deleteDriverAttribute(Long id);

    /**
     * 获得连接配置信息
     *
     * @param id 编号
     * @return 连接配置信息
     */
    DriverAttributeDO getDriverAttribute(Long id);

    /**
     * 获得连接配置信息列表
     *
     * @param ids 编号
     * @return 连接配置信息列表
     */
    List<DriverAttributeDO> getDriverAttributeList(Collection<Long> ids);

    /**
     * 获得连接配置信息分页
     *
     * @param pageReqVO 分页查询
     * @return 连接配置信息分页
     */
    PageResult<DriverAttributeDO> getDriverAttributePage(DriverAttributePageReqVO pageReqVO);

    /**
     * 获得连接配置信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 连接配置信息列表
     */
    List<DriverAttributeDO> getDriverAttributeList(DriverAttributeExportReqVO exportReqVO);

    /**
     * 根据驱动配置属性 NAME 和 驱动 ID 查询
     *
     * @param name     属性名称
     * @param driverId 驱动ID
     * @return DriverAttribute
     */
    DriverAttributeDO selectByNameAndDriverId(String name, Long driverId);

    /**
     * 根据驱动 ID 查询
     *
     * @param driverId 驱动ID
     * @return DriverAttribute Array
     */
    List<DriverAttributeDO> selectByDriverId(Long driverId);

    DriverAttributeDO selectById(Long id);
}
