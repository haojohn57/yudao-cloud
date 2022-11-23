package cn.iocoder.yudao.module.driver.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.iocoder.yudao.framework.common.enums.DeviceConstants;
import cn.iocoder.yudao.framework.common.enums.DriverConstants;
import cn.iocoder.yudao.framework.common.iot.bean.driver.AttributeInfo;
import cn.iocoder.yudao.framework.common.iot.model.Device;
import cn.iocoder.yudao.framework.common.iot.model.Point;
import cn.iocoder.yudao.framework.driversdk.core.bean.driver.DriverContext;
import cn.iocoder.yudao.framework.driversdk.core.service.DriverCustomService;
import cn.iocoder.yudao.framework.driversdk.core.service.DriverServiceSdk;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 益莲科技
 */
@Slf4j
@Service
public class DriverCustomServiceImpl implements DriverCustomService {

    @Resource
    private DriverContext driverContext;
    @Resource
    private DriverServiceSdk driverServiceSdk;

    @Override
    public void initial() {
    }

    @Override
    public String read(Map<String, AttributeInfo> driverInfo, Map<String, AttributeInfo> pointInfo, Device device, Point point) throws Exception {
        if (point.getType().equals("string")) {
            return RandomUtil.randomString(8);
        }
        if (point.getType().equals("boolean")) {
            return String.valueOf(RandomUtil.randomBoolean());
        }
        return String.valueOf(RandomUtil.randomDouble(100));
    }

    @Override
    public Boolean write(Map<String, AttributeInfo> driverInfo, Map<String, AttributeInfo> pointInfo, Device device, AttributeInfo value) throws Exception {
        return false;
    }

    @Override
    public void schedule() {

        /*
        TODO:设备状态
        上传设备状态，可自行灵活拓展，不一定非要在schedule()接口中实现，也可以在read中实现设备状态的设置；
        你可以通过某种判断机制确定设备的状态，然后通过driverService.deviceEventSender接口将设备状态交给SDK管理。

        设备状态（DeviceStatus）如下：
        ONLINE:在线
        OFFLINE:离线
        MAINTAIN:维护
        FAULT:故障
         */
        driverContext.getDriverMetadata().getDeviceMap().keySet().forEach(id -> driverServiceSdk.deviceEventSender(id, DeviceConstants.Event.HEARTBEAT, DriverConstants.Status.ONLINE));
    }

}
