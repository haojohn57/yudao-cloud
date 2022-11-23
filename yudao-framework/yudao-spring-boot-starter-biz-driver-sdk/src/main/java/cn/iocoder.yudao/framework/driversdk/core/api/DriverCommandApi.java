package cn.iocoder.yudao.framework.driversdk.core.api;

import cn.iocoder.yudao.framework.common.iot.bean.driver.command.CmdParameter;
import cn.iocoder.yudao.framework.common.iot.bean.point.PointValue;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.validation.Read;
import cn.iocoder.yudao.framework.common.validation.ValidatableList;
import cn.iocoder.yudao.framework.driversdk.core.enums.ApiConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * DriverCommand FeignClient
 *
 * @author 益莲科技
 */
@FeignClient(name = ApiConstants.NAME) // TODO 益莲：fallbackFactory =
@Api(tags = "RPC 服务 - 驱动")

public interface DriverCommandApi {
    String PREFIX = ApiConstants.PREFIX + ApiConstants.COMMAND_URL_PREFIX;
    /**
     * 读
     *
     * @param cmdParameters list<{deviceId,pointId}>
     * @return R<List < PointValue>>
     */
    @PostMapping(PREFIX + "/read")
    @ApiOperation("读取位号信息")
    public CommonResult<List<PointValue>> readPoint(@Validated(Read.class) @RequestBody ValidatableList<CmdParameter> cmdParameters);

    /**
     * 写
     *
     * @param cmdParameters list<{deviceId,pointId,stringValue}>
     * @return R<Boolean>
     */
    @PostMapping(PREFIX + "/write")
    @ApiOperation("写位号信息")
    public CommonResult<Boolean> writePoint(@Validated(Read.class) @RequestBody ValidatableList<CmdParameter> cmdParameters);

}
