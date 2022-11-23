package cn.iocoder.yudao.module.system.service.iot;

import cn.iocoder.yudao.framework.common.iot.bean.point.PointDetail;

/**
 * Auto Interface
 *
 * @author 益莲科技
 */
public interface AutoService {
    /**
     * 自动创建设备和位号
     *
     * @param deviceName Device Name
     * @param pointName  Point Name
     * @param driverId   Driver Id
     * @param tenantId   Tenant Id
     * @return booleaDevicePointn
     */
    PointDetail autoCreateDeviceAndPoint(String deviceName, String pointName, Long driverId, Long tenantId);
}
