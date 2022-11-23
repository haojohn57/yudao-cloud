package cn.iocoder.yudao.module.system.api.iot.device.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * 设备 Response DTO
 *
 * @author 芋道源码
 */
@Data
public class DeviceRespDTO {


    /**
     * 部门编号
     */
    private Long id;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 是否结构化存储数据
     * 默认：false，为单点存储
     */
    private Integer multi;

    private Integer enable;

    private Set<Long> profileIds = new HashSet<>(8);

    private Long driverId;

    private Long deptId;

    private Long tenantId;

}

