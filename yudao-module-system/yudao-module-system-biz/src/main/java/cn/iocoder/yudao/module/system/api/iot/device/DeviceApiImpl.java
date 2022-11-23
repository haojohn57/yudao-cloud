package cn.iocoder.yudao.module.system.api.iot.device;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.api.iot.device.dto.DeviceRespDTO;
import cn.iocoder.yudao.module.system.convert.iot.device.DeviceConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.device.DeviceDO;
import cn.iocoder.yudao.module.system.service.iot.device.DeviceService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.module.system.enums.ApiConstants.VERSION;

/**
 * 设备 FeignClient
 *
 * @author 益莲科技
 */
@RestController // 提供 RESTful API 接口，给 Feign 调用
@DubboService(version = VERSION) // 提供 Dubbo RPC 接口，给 Dubbo Consumer 调用
@Validated
public class DeviceApiImpl implements DeviceApi {

    @Resource
    private DeviceService deviceService;

    /**
     * 根据 ID 查询 Device
     *
     * @param id Device Id
     * @return R<Device>
     */
    @Override
    public CommonResult<DeviceRespDTO> selectById(Long id) {
        DeviceDO device = deviceService.selectById(id);
        return success(DeviceConvert.INSTANCE.convert3(device));
    }
    /**
     * 根据 驱动ID 查询 Device
     *
     * @param driverId Driver Id
     * @return R<Device>
     */
    @Override
    public CommonResult<List<DeviceRespDTO>> selectByDriverId(Long driverId) {
        List<DeviceDO> device = deviceService.selectByDriverId(driverId);
        return success(DeviceConvert.INSTANCE.convertList03(device));
    }

    /**
     * 根据 模板ID 查询 Device
     *
     * @param profileId Profile Id
     * @return CommonResult<DeviceRespDTO>
     */
    @Override
    public CommonResult<List<DeviceRespDTO>> selectByProfileId(Long profileId){
        List<DeviceDO> device = deviceService.selectByProfileId(profileId);
        return success(DeviceConvert.INSTANCE.convertList03(device));
    }

}
