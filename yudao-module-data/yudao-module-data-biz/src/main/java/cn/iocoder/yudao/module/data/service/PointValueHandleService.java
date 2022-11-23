package cn.iocoder.yudao.module.data.service;

import cn.iocoder.yudao.framework.common.iot.bean.point.PointValue;

import java.util.List;

/**
 * 用户自定义数据处理服务接口
 *
 * @author 益莲科技
 */
public interface PointValueHandleService {

    /**
     * 自定义数据处理，此处可以自定义逻辑，将数据存放到别的数据库，或者发送到别的地方
     *
     * @param pointValue PointValue
     */
    void postHandle(PointValue pointValue);

    /**
     * 自定义数据处理，此处可以自定义逻辑，将数据存放到别的数据库，或者发送到别的地方
     *
     * @param pointValues PointValue Array
     */
    void postHandle(List<PointValue> pointValues);

}
