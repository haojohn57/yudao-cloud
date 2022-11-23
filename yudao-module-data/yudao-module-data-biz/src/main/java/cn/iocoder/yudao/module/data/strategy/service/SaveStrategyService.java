package cn.iocoder.yudao.module.data.strategy.service;

import cn.iocoder.yudao.framework.common.iot.bean.point.PointValue;

import java.util.List;

/**
 * Point Value 存储策略服务接口
 *
 * @author 益莲科技
 */
public interface SaveStrategyService {

    /**
     * 保存 PointValue
     *
     * @param pointValue PointValue
     */
    void savePointValue(PointValue pointValue);

    /**
     * 保存 PointValue 集合
     *
     * @param pointValues PointValue Array
     */
    void savePointValues(List<PointValue> pointValues);
}
