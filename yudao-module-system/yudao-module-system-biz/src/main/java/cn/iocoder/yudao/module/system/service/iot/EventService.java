package cn.iocoder.yudao.module.system.service.iot;

import cn.iocoder.yudao.framework.common.iot.dto.DeviceEventDto;
import cn.iocoder.yudao.framework.common.iot.dto.DriverEventDto;
import cn.iocoder.yudao.framework.common.iot.model.DeviceEvent;
import cn.iocoder.yudao.framework.common.iot.model.DriverEvent;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @author 益莲科技
 */
public interface EventService {

    /**
     * 新增 DriverEvent
     *
     * @param driverEvent DriverEvent
     */
    void addDriverEvent(DriverEvent driverEvent);

    /**
     * 批量新增 DriverEvent
     *
     * @param driverEvents DriverEvent Array
     */
    void addDriverEvents(List<DriverEvent> driverEvents);

    /**
     * 新增 DeviceEvent
     *
     * @param deviceEvent DeviceEvent
     */
    void addDeviceEvent(DeviceEvent deviceEvent);

    /**
     * 批量新增 DeviceEvent
     *
     * @param deviceEvents DeviceEvent Array
     */
    void addDeviceEvents(List<DeviceEvent> deviceEvents);

    /**
     * 获取 DriverEvent 带分页、排序
     *
     * @param driverEventDto DriverEventDto
     * @return Page<DriverEvent>
     */
    Page<DriverEvent> driverEvent(DriverEventDto driverEventDto);

    /**
     * 获取 DeviceEvent 带分页、排序
     *
     * @param deviceEventDto DeviceEventDto
     * @return Page<DeviceEvent>
     */
    Page<DeviceEvent> deviceEvent(DeviceEventDto deviceEventDto);

}
