package cn.iocoder.yudao.framework.common.iot.bean.driver;

import cn.iocoder.yudao.framework.common.iot.model.Device;
import cn.iocoder.yudao.framework.common.iot.model.DriverAttribute;
import cn.iocoder.yudao.framework.common.iot.model.Point;
import cn.iocoder.yudao.framework.common.iot.model.PointAttribute;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Driver Metadata
 *
 * @author 益莲科技
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DriverMetadata implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long driverId;
    private Long tenantId;
    private Map<Long, DriverAttribute> driverAttributeMap;
    private Map<Long, PointAttribute> pointAttributeMap;

    /**
     * deviceId(driverAttribute.name,(driverInfo.value,driverAttribute.type))
     */
    private Map<Long, Map<String, AttributeInfo>> driverInfoMap;

    /**
     * deviceId(pointId(pointAttribute.name,(pointInfo.value,pointAttribute.type)))
     */
    private Map<Long, Map<Long, Map<String, AttributeInfo>>> pointInfoMap;

    /**
     * deviceId,device
     */
    private Map<Long, Device> deviceMap;

    /**
     * profileId(pointId,point)
     */
    private Map<Long, Map<Long, Point>> profilePointMap;

    public DriverMetadata() {
        this.driverAttributeMap = new ConcurrentHashMap<>(16);
        this.pointAttributeMap = new ConcurrentHashMap<>(16);
        this.deviceMap = new ConcurrentHashMap<>(16);
        this.driverInfoMap = new ConcurrentHashMap<>(16);
        this.pointInfoMap = new ConcurrentHashMap<>(16);
        this.profilePointMap = new ConcurrentHashMap<>(16);
    }
}
