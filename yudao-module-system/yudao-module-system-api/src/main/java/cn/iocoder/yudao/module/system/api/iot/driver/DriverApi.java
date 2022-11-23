package cn.iocoder.yudao.module.system.api.iot.driver;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.api.iot.driver.dto.DriverRespDTO;
import cn.iocoder.yudao.module.system.enums.ApiConstants;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotNull;

/**
 * 驱动 FeignClient
 *
 * @author 益莲科技
 */
@FeignClient(name = ApiConstants.NAME) // TODO 芋艿：fallbackFactory =
@Api(tags = "RPC 服务 - 驱动")
public interface DriverApi {

    String PREFIX = ApiConstants.PREFIX + "/driver";
    /**
     * 根据 ID 查询 Driver
     *
     * @param id Driver Id
     * @return Driver
     */
    @GetMapping(PREFIX + "/id/{id}")
    CommonResult<DriverRespDTO> selectById(@NotNull @PathVariable(value = "id") Long id);

    /**
     * 根据 SERVICENAME 查询 Driver
     *
     * @param serviceName Driver Service Name
     * @return Driver
     */
    @GetMapping(PREFIX + "/service/{serviceName}")
    CommonResult<DriverRespDTO> selectByServiceName(@NotNull @PathVariable(value = "serviceName") String serviceName);

    /**
     * 根据 TYPE & HOST & PORT 查询 Driver
     *
     * @param type Driver type
     * @param host Driver Host
     * @param port Driver Port
     * @return Driver
     */
    @GetMapping(PREFIX + "/type/{type}/host/{host}/port/{port}")
    CommonResult<DriverRespDTO> selectByHostPort(@NotNull @PathVariable(value = "type") String type, @NotNull @PathVariable(value = "host") String host, @NotNull @PathVariable(value = "port") Integer port, Long tenantId);

}
