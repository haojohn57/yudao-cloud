package cn.iocoder.yudao.module.system.service.iot;

import cn.iocoder.yudao.module.system.controller.admin.iot.device.vo.DevicePageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driver.vo.DriverPageReqVO;

import java.util.Map;

/**
 * Device Interface
 *
 * @author 益莲科技
 */
public interface StatusService {

    /**
     * 根据 驱动ServiceName 查询 Driver 服务状态
     *
     * @param serviceName Driver ServiceName
     * @return String
     */
    String driver(String serviceName);

    /**
     * 分页查询 Driver 服务状态，同驱动分页查询配套使用
     *
     * @param  pageReqVO
     * @return Map<String, String>
     */
    Map<String, String> driver(DriverPageReqVO pageReqVO);

    /**
     * 根据 设备Id 查询 Device 服务状态
     *
     * @param id Device Id
     * @return String
     */
    String device(Long id);

    /**
     * 分页查询 Device 服务状态，同设备分页查询配套使用
     *
     * @param  pageReqVO
     * @return Map<String, String>
     */
    Map<String, String> device(DevicePageReqVO pageReqVO);

    /**
     * 根据 模板ID 查询 Device 服务状态
     *
     * @param profileId Profile Id
     * @return Map<String, String>
     */
    Map<String, String> deviceByProfileId(Long profileId);
}
