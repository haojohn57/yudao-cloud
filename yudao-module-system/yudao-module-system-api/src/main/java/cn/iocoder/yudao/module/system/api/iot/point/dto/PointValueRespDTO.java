package cn.iocoder.yudao.module.system.api.iot.point.dto;

import cn.iocoder.yudao.framework.common.iot.bean.point.PointValue;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 位号数值 Response DTO
 *
 * @author 芋道源码
 */
@Data
public class PointValueRespDTO {

    @MongoId
    private Long id;

    /**
     * 设备ID，同MySQl中等 设备ID 一致
     */
    private Long deviceId;

    /**
     * 位号ID，同MySQl中等 位号ID 一致
     */
    private Long pointId;

    /**
     * 处理值，进行过缩放、格式化等操作
     */
    private String value;

    /**
     * 原始值
     */
    private String rawValue;

    /**
     * 计算值
     */
    private Object calculateValue;

    private List<PointValue> children;

    @Transient
    private Short rw;

    @Transient
    private String unit;

    @Transient
    private String type;

    @Transient
    private Integer timeOut = 15;

    @Transient
    private TimeUnit timeUnit = TimeUnit.MINUTES;

    private Boolean multi;

    private Date originTime;

    private Date createTime;

}
