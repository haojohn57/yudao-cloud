package cn.iocoder.yudao.framework.driversdk.core.api;

import cn.iocoder.yudao.framework.common.enums.DriverConstants;
import cn.iocoder.yudao.framework.common.iot.bean.driver.command.CmdParameter;
import cn.iocoder.yudao.framework.common.iot.bean.point.PointValue;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.validation.ValidatableList;
import cn.iocoder.yudao.framework.driversdk.core.service.DriverCommandService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static cn.iocoder.yudao.framework.driversdk.core.enums.ApiConstants.VERSION;

/**
 * 驱动操作指令 Rest Api
 *
 * @author 益莲科技
 */
@RestController // 提供 RESTful API 接口，给 Feign 调用
@DubboService(version = VERSION) // 提供 Dubbo RPC 接口，给 Dubbo Consumer 调用
@Validated
public class DriverCommandApiImpl implements DriverCommandApi {

    @Resource
    private DriverCommandService driverCommandService;

    @Override
    public CommonResult<List<PointValue>> readPoint(ValidatableList<CmdParameter> cmdParameters) {
        List<PointValue> pointValues = new ArrayList<>(16);
        try {
            if (cmdParameters.size() > DriverConstants.DEFAULT_MAX_REQUEST_SIZE) {
                return CommonResult.error(1002026004,"point request size are limited to " + DriverConstants.DEFAULT_MAX_REQUEST_SIZE);//Todo:code
            }
            cmdParameters.forEach(cmdParameter -> {
                PointValue pointValue = driverCommandService.read(cmdParameter.getDeviceId(), cmdParameter.getPointId());
                Optional.ofNullable(pointValue).ifPresent(pointValues::add);
            });
        } catch (Exception e) {
            return CommonResult.error(1002026005,(e.getMessage()));//Todo:code1
        }
        return CommonResult.success((pointValues));
    }

    @Override
    public CommonResult<Boolean> writePoint(ValidatableList<CmdParameter> cmdParameters) {
        try {
            if (cmdParameters.size() > DriverConstants.DEFAULT_MAX_REQUEST_SIZE) {
                return CommonResult.error(1002026004,"point request size are limited to " + DriverConstants.DEFAULT_MAX_REQUEST_SIZE);
            }
            cmdParameters.forEach(cmdParameter -> driverCommandService.write(cmdParameter.getDeviceId(), cmdParameter.getPointId(), cmdParameter.getValue()));
        } catch (Exception e) {
            return CommonResult.error(1002026005,e.getMessage()); //Todo:code
        }
        return CommonResult.success(true);
    }
}
