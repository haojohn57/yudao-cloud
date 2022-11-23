package cn.iocoder.yudao.module.system.service.iot;

import cn.iocoder.yudao.framework.common.enums.CacheConstant;
import cn.iocoder.yudao.framework.common.enums.DriverConstants;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.iot.device.vo.DevicePageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driver.vo.DriverPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.device.DeviceDO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.driver.DriverDO;
import cn.iocoder.yudao.module.system.service.iot.device.DeviceService;
import cn.iocoder.yudao.module.system.service.iot.driver.DriverService;
import cn.iocoder.yudao.module.system.service.iot.profilebind.ProfileBindService;
import cn.iocoder.yudao.framework.common.util.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * DeviceService Impl
 *
 * @author 益莲科技
 */
@Slf4j
@Service
public class StatusServiceImpl implements StatusService {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private DriverService driverService;
    @Resource
    private DeviceService deviceService;
    @Resource
    private ProfileBindService profileBindService;

    @Override
    public String driver(String serviceName) {
        String key = CacheConstant.Prefix.DRIVER_STATUS_KEY_PREFIX + serviceName;
        String status = redisUtil.getKey(key, String.class);
        status = null != status ? status : DriverConstants.Status.OFFLINE;
        return status;
    }

    @Override
    public Map<String, String> driver(DriverPageReqVO pageReqVO) {
        Map<String, String> statusMap = new HashMap<>(16);
        PageResult<DriverDO> pageResult = driverService.getDriverPage(pageReqVO);
        pageResult.getList().forEach(driver -> {
            String key = CacheConstant.Prefix.DRIVER_STATUS_KEY_PREFIX + driver.getServiceName();
            String status = redisUtil.getKey(key, String.class);
            status = null != status ? status : DriverConstants.Status.OFFLINE;
            statusMap.put(String.valueOf(driver.getId()), status);
        });
        return statusMap;
    }

    @Override
    public String device(Long id) {
        String key = CacheConstant.Prefix.DEVICE_STATUS_KEY_PREFIX + id;
        String status = redisUtil.getKey(key, String.class);
        status = null != status ? status : DriverConstants.Status.OFFLINE;
        return status;
    }

    @Override
    public Map<String, String> device(DevicePageReqVO pageReqVO) {
        Map<String, String> statusMap = new HashMap<>(16);

        PageResult<DeviceDO> pageResult = deviceService.getDevicePage(pageReqVO);
        pageResult.getList().forEach(device -> {
            String key = CacheConstant.Prefix.DEVICE_STATUS_KEY_PREFIX + device.getId();
            String status = redisUtil.getKey(key, String.class);
            status = null != status ? status : DriverConstants.Status.OFFLINE;
            statusMap.put(String.valueOf(device.getId()), status);
        });
        return statusMap;
    }

    @Override
    public Map<String, String> deviceByProfileId(Long profileId) {
        Map<String, String> statusMap = new HashMap<>(16);

        profileBindService.selectDeviceIdByProfileId(profileId).forEach(id -> {
            String key = CacheConstant.Prefix.DEVICE_STATUS_KEY_PREFIX + id;
            String status = redisUtil.getKey(key, String.class);
            status = null != status ? status : DriverConstants.Status.OFFLINE;
            statusMap.put(String.valueOf(id), status);
        });
        return statusMap;
    }

}
