package cn.iocoder.yudao.module.data.api.point;


import cn.iocoder.yudao.framework.common.iot.bean.point.PointValue;
import cn.iocoder.yudao.framework.common.iot.dto.PointValueDto;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.data.enums.ApiConstants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 数据 FeignClient
 *
 * @author 益莲科技
 */

@FeignClient(name = ApiConstants.NAME) // TODO 芋艿：fallbackFactory =
@Api(tags = "RPC 服务 - 数据处理")
public interface PointValueApi {

    String PREFIX = ApiConstants.PREFIX + "point_value";

    @GetMapping(PREFIX + "/latest/device_id/{deviceId}")
    @ApiOperation("查询最新 PointValue 集合")
    @ApiImplicitParam(name = "deviceId", value = "设备编号", example = "1024", required = true, dataTypeClass = Long.class)
    CommonResult<List<PointValue>> latest(@NotNull @RequestParam("deviceId") Long deviceId, @RequestParam(required = false, defaultValue = "false") Boolean history);

    @GetMapping(PREFIX + "/latest/device_id/{deviceId}/point_id/{pointId}")
    @ApiOperation("查询最新 PointValue")
    @ApiImplicitParam(name = "deviceId", value = "设备编号", example = "1,2", required = true, allowMultiple = true)
    CommonResult<PointValue> latest(@NotNull @RequestParam("deviceId") Long deviceId, @NotNull @RequestParam("pointId") Long pointId, @RequestParam(required = false, defaultValue = "false") Boolean history);


    @GetMapping(PREFIX + "/list")
    @ApiOperation("分页查询 PointValue")
    @ApiImplicitParam(name = "PointValueDto", value = "部门编号数组", example = "1,2", required = true, allowMultiple = true)
    CommonResult<Page<PointValue>> list(@RequestBody(required = false) PointValueDto pointValueDto);

}
