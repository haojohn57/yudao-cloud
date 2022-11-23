package cn.iocoder.yudao.module.system.api.iot.driver;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.api.iot.driver.dto.DriverRespDTO;
import cn.iocoder.yudao.module.system.convert.iot.driver.DriverConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.driver.DriverDO;
import cn.iocoder.yudao.module.system.service.iot.driver.DriverService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.module.system.enums.ApiConstants.VERSION;

/**
 * 驱动 Client 接口实现
 *
 * @author 益莲科技
 */
@RestController // 提供 RESTful API 接口，给 Feign 调用
@DubboService(version = VERSION) // 提供 Dubbo RPC 接口，给 Dubbo Consumer 调用
@Validated
public class DriverApiImpl implements DriverApi {
    @Resource
    private DriverService driverService;

    @Override
    public CommonResult<DriverRespDTO> selectById(Long id) {
        DriverDO select = driverService.selectById(id);
        return success(DriverConvert.INSTANCE.convert5(select));
    }

    @Override
    public CommonResult<DriverRespDTO> selectByServiceName(String serviceName) {
        DriverDO select = driverService.selectByServiceName(serviceName);
        return success(DriverConvert.INSTANCE.convert5(select));
    }

    @Override
    public CommonResult<DriverRespDTO> selectByHostPort(String type, String host, Integer port, Long tenantId) {
        DriverDO select = driverService.selectByHostPort(type, host, port, tenantId);
        return success(DriverConvert.INSTANCE.convert5(select));
    }
}
