package cn.iocoder.yudao.module.system.service.iot.pointinfo;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointinfo.vo.PointInfoCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointinfo.vo.PointInfoExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointinfo.vo.PointInfoPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointinfo.vo.PointInfoUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.point.PointInfoDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 位号配置信息 Service 接口
 *
 * @author 益莲科技
 */
public interface PointInfoService {

    /**
     * 创建位号配置信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPointInfo(@Valid PointInfoCreateReqVO createReqVO);

    /**
     * 更新位号配置信息
     *
     * @param updateReqVO 更新信息
     */
    void updatePointInfo(@Valid PointInfoUpdateReqVO updateReqVO);

    /**
     * 删除位号配置信息
     *
     * @param id 编号
     */
    void deletePointInfo(Long id);

    /**
     * 获得位号配置信息
     *
     * @param id 编号
     * @return 位号配置信息
     */
    PointInfoDO getPointInfo(Long id);

    /**
     * 获得位号配置信息列表
     *
     * @param ids 编号
     * @return 位号配置信息列表
     */
    List<PointInfoDO> getPointInfoList(Collection<Long> ids);

    /**
     * 获得位号配置信息分页
     *
     * @param pageReqVO 分页查询
     * @return 位号配置信息分页
     */
    PageResult<PointInfoDO> getPointInfoPage(PointInfoPageReqVO pageReqVO);

    /**
     * 获得位号配置信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 位号配置信息列表
     */
    List<PointInfoDO> getPointInfoList(PointInfoExportReqVO exportReqVO);

    PointInfoDO selectById(Long id);

    /**
     * 根据位号配置信息 ID & 设备 ID & 位号 ID 查询
     *
     * @param pointAttributeId Point Attribute Id
     * @param deviceId         Device Id
     * @param pointId          Point Id
     * @return PointInfo
     */
    PointInfoDO selectByAttributeIdAndDeviceIdAndPointId(Long pointAttributeId, Long deviceId, Long pointId);

    /**
     * 根据位号配置信息 ID 查询
     *
     * @param pointAttributeId Point Attribute Id
     * @return PointInfo Array
     */
    List<PointInfoDO> selectByAttributeId(Long pointAttributeId);

    /**
     * 根据 设备 ID 查询
     *
     * @param deviceId Device Id
     * @return PointInfo Array
     */
    List<PointInfoDO> selectByDeviceId(Long deviceId);

    /**
     * 根据 设备 ID & 位号 ID 查询
     *
     * @param deviceId Device Id
     * @param pointId  Point Id
     * @return PointInfo Array
     */
    List<PointInfoDO> selectByDeviceIdAndPointId(Long deviceId, Long pointId);

}
