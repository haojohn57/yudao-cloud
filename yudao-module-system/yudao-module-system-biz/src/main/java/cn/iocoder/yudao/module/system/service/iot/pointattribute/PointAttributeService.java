package cn.iocoder.yudao.module.system.service.iot.pointattribute;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointattribute.vo.PointAttributeCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointattribute.vo.PointAttributeExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointattribute.vo.PointAttributePageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointattribute.vo.PointAttributeUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.point.PointAttributeDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 位号配置信息 Service 接口
 *
 * @author 益莲科技
 */
public interface PointAttributeService {

    /**
     * 创建位号配置信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPointAttribute(@Valid PointAttributeCreateReqVO createReqVO);

    /**
     * 更新位号配置信息
     *
     * @param updateReqVO 更新信息
     */
    void updatePointAttribute(@Valid PointAttributeUpdateReqVO updateReqVO);

    /**
     * 删除位号配置信息
     *
     * @param id 编号
     */
    void deletePointAttribute(Long id);

    /**
     * 获得位号配置信息
     *
     * @param id 编号
     * @return 位号配置信息
     */
    PointAttributeDO getPointAttribute(Long id);

    /**
     * 获得位号配置信息列表
     *
     * @param ids 编号
     * @return 位号配置信息列表
     */
    List<PointAttributeDO> getPointAttributeList(Collection<Long> ids);

    /**
     * 获得位号配置信息分页
     *
     * @param pageReqVO 分页查询
     * @return 位号配置信息分页
     */
    PageResult<PointAttributeDO> getPointAttributePage(PointAttributePageReqVO pageReqVO);

    /**
     * 获得位号配置信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 位号配置信息列表
     */
    List<PointAttributeDO> getPointAttributeList(PointAttributeExportReqVO exportReqVO);

    PointAttributeDO selectById(String id);

    /**
     * 根据位号配置属性 NAME 和 驱动 ID 查询
     *
     * @param name
     * @param driverId
     * @return PointAttribute
     */
    PointAttributeDO selectByNameAndDriverId(String name, Long driverId);

    /**
     * 根据驱动 ID 查询
     *
     * @param driverId
     * @return PointAttribute Array
     */
    List<PointAttributeDO> selectByDriverId(Long driverId);

}
