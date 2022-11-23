package cn.iocoder.yudao.module.system.api.iot.device;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.api.iot.device.dto.DeviceRespDTO;
import cn.iocoder.yudao.module.system.enums.ApiConstants;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 设备 FeignClient
 *
 * @author 益莲科技
 */
@FeignClient(name = ApiConstants.NAME) // TODO 芋艿：fallbackFactory =
@Api(tags = "RPC 服务 - 设备")
public interface DeviceApi {

    String PREFIX = ApiConstants.PREFIX + "/device";

    /**
     * 根据 ID 查询 Device
     *
     * @param id Device Id
     * @return R<Device>
     */
    @GetMapping(PREFIX + "/id/{id}")
    CommonResult<DeviceRespDTO> selectById(@NotNull @PathVariable(value = "id") Long id);

    /**
     * 根据 驱动ID 查询 Device
     *
     * @param driverId Driver Id
     * @return R<Device>
     */
    @GetMapping(PREFIX + "/driver_id/{driverId}")
    CommonResult<List<DeviceRespDTO>> selectByDriverId(@NotNull @PathVariable(value = "driverId") Long driverId);

    /**
     * 根据 模板ID 查询 Device
     *
     * @param profileId Profile Id
     * @return CommonResult<DeviceRespDTO>
     */
    @GetMapping(PREFIX + "/profile_id/{profileId}")
    CommonResult<List<DeviceRespDTO>> selectByProfileId(@NotNull @PathVariable(value = "profileId") Long profileId);
}
