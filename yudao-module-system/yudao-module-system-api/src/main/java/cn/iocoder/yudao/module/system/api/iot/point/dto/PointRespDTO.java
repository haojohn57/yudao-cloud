package cn.iocoder.yudao.module.system.api.iot.point.dto;

import lombok.Data;

/**
 * 位号数值 Response DTO
 *
 * @author 芋道源码
 */
@Data
public class PointRespDTO {

    private Long id;
    /**
     * 设备名称
     */
    private String deviceName;

    private String pointName;

    private Long driverId;

    private Long deviceId;
    private Long pointId;

    private Short rw;
    private String type;
    private String unit;

}
