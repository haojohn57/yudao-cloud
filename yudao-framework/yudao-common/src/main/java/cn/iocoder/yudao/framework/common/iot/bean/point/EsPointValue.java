package cn.iocoder.yudao.framework.common.iot.bean.point;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * MongoDB 位号数据
 *
 * @author 益莲科技
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EsPointValue implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

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

    private List<EsPointValue> children;

    private Boolean multi;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date originTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date createTime;

    public EsPointValue(Long pointId, String rawValue, String value) {
        this.pointId = pointId;
        this.rawValue = rawValue;
        this.value = value;
    }

    public EsPointValue(Long deviceId, Long pointId, String rawValue, String value) {
        this.deviceId = deviceId;
        this.pointId = pointId;
        this.rawValue = rawValue;
        this.value = value;
        this.originTime = new Date();
    }

    public EsPointValue(Long deviceId, List<EsPointValue> children) {
        this.deviceId = deviceId;
        this.children = children;
        this.originTime = new Date();
    }
}
