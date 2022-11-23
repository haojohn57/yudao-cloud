package cn.iocoder.yudao.module.system.api.iot.point;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.api.iot.point.dto.PointRespDTO;
import cn.iocoder.yudao.module.system.enums.ApiConstants;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 位号 FeignClient
 *
 * @author 益莲科技
 */
@FeignClient(name = ApiConstants.NAME) // TODO 芋艿：fallbackFactory =
@Api(tags = "RPC 服务 - 位号")
public interface PointApi {
    String PREFIX = ApiConstants.PREFIX + "/point";
    /**
     * 根据 ID 查询 Point
     *
     * @param id Point Id
     * @return Point
     */
    @GetMapping(PREFIX + "/id/{id}")
    CommonResult<PointRespDTO> selectById(@NotNull @PathVariable(value = "id") Long id);

    /**
     * 根据 设备 ID 查询 Point
     *
     * @param deviceId Device Id
     * @return Point Array
     */
    @GetMapping(PREFIX + "/device_id/{deviceId}")
    CommonResult<List<PointRespDTO>> selectByDeviceId(@NotNull @PathVariable(value = "deviceId") Long deviceId);

    /**
     * 根据 模板 ID 查询 Point
     *
     * @param profileId Profile Id
     * @return Point Array
     */
    @GetMapping(PREFIX + "/profile_id/{profileId}")
    CommonResult<List<PointRespDTO>> selectByProfileId(@NotNull @PathVariable(value = "profileId") Long profileId);

    /**
     * 查询 位号单位
     *
     * @param pointIds Point Id Set
     * @return Map<String, String>
     */
    @PostMapping(PREFIX + "/unit")
    CommonResult<Map<Long, String>> unit(@RequestBody(required = false) Set<Long> pointIds);
}
