package cn.iocoder.yudao.module.system.service.iot.point;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.iot.point.vo.PointCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.point.vo.PointExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.point.vo.PointPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.point.vo.PointUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.point.PointDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 设备位号 Service 接口
 *
 * @author 益莲科技
 */
public interface PointService {

    /**
     * 创建设备位号
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPoint(@Valid PointCreateReqVO createReqVO);

    /**
     * 更新设备位号
     *
     * @param updateReqVO 更新信息
     */
    void updatePoint(@Valid PointUpdateReqVO updateReqVO);

    /**
     * 删除设备位号
     *
     * @param id 编号
     */
    void deletePoint(Long id);

    /**
     * 获得设备位号
     *
     * @param id 编号
     * @return 设备位号
     */
    PointDO getPoint(Long id);

    /**
     * 获得设备位号列表
     *
     * @param ids 编号
     * @return 设备位号列表
     */
    List<PointDO> getPointList(Collection<Long> ids);

    /**
     * 获得设备位号分页
     *
     * @param pageReqVO 分页查询
     * @return 设备位号分页
     */
    PageResult<PointDO> getPointPage(PointPageReqVO pageReqVO);

    /**
     * 获得设备位号列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 设备位号列表
     */
    List<PointDO> getPointList(PointExportReqVO exportReqVO);

    /**
     * 根据 位号Name & 模板Id 查询位号
     *
     * @param name      Point Name
     * @param profileId Profile Id
     * @return Point
     */
    PointDO selectByNameAndProfileId(String name, Long profileId);

    /**
     * 根据 设备Id 查询位号
     *
     * @param deviceId Device Id
     * @return Point Array
     */
    List<PointDO> selectByDeviceId(Long deviceId);

    /**
     * 根据 模板Id 查询位号
     *
     * @param profileId Profile Id
     * @return Point Array
     */
    List<PointDO> selectByProfileId(Long profileId);

    /**
     * 根据 模板Id 集查询位号
     *
     * @param profileIds Profile Id Set
     * @return Point Array
     */
    List<PointDO> selectByProfileIds(Set<Long> profileIds);

    /**
     * 查询 位号单位
     *
     * @param pointIds Point Id Set
     * @return Map<Long, String>
     */
    Map<Long, String> unit(Set<Long> pointIds);

    PointDO selectById(Long id);
}
