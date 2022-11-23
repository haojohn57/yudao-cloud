package cn.iocoder.yudao.framework.common.iot.message;

import cn.iocoder.yudao.framework.common.iot.bean.point.PointValue;
import lombok.Data;

import javax.annotation.Resource;

/**
 * 点位数据刷新 Message
 *
 * @author 益莲科技
 */
@Data
public class PointMessage {

    @Resource
    private PointValue pointValue;

    public PointMessage() {
    }

    public PointMessage(PointValue pointValue) {
        this.pointValue = pointValue;
    }

}
