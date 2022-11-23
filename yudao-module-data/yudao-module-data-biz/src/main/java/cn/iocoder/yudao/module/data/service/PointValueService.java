package cn.iocoder.yudao.module.data.service;

import cn.iocoder.yudao.framework.common.iot.bean.point.PointValue;
import cn.iocoder.yudao.framework.common.iot.dto.PointValueDto;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @author 益莲科技
 */
public interface PointValueService {

    /**
     * 新增 PointValue
     *
     * @param pointValue PointValue
     */
    void savePointValue(PointValue pointValue);

    /**
     * 批量新增 PointValue
     *
     * @param pointValues PointValue Array
     */
    void savePointValues(List<PointValue> pointValues);

    /**
     * 获取实时数据
     *
     * @param deviceId Device Id
     * @return PointValue Array
     */
    List<PointValue> realtime(Long deviceId);

    /**
     * 获取实时数据
     *
     * @param deviceId Device Id
     * @param pointId  Point Id
     * @return PointValue
     */
    PointValue realtime(Long deviceId, Long pointId);

    /**
     * 获取一个设备最新的位号数据集合
     *
     * @param deviceId Device Id
     * @return PointValue Array
     */
    List<PointValue> latest(Long deviceId);

    /**
     * 获取最新的一个位号数据
     *
     * @param deviceId Device Id
     * @param pointId  Point Id
     * @return PointValue
     */
    PointValue latest(Long deviceId, Long pointId);

    /**
     * 获取带分页、排序
     *
     * @param pointValueDto PointValueDto
     * @return Page<PointValue>
     */
    Page<PointValue> list(PointValueDto pointValueDto);

}
