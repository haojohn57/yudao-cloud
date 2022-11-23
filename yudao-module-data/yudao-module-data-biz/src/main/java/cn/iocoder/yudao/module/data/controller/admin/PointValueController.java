package cn.iocoder.yudao.module.data.controller.admin;

import cn.iocoder.yudao.framework.common.iot.bean.point.PointValue;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.data.controller.admin.vo.PointValueRespVO;
import cn.iocoder.yudao.module.data.convert.PointValueConvert;
import cn.iocoder.yudao.module.data.service.PointValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Api(tags = "管理后台 - 位号数值")
@RestController
@RequestMapping("/data/point-value")
@Validated
public class PointValueController {

    @Resource
    private PointValueService pointValueService;

    @GetMapping("/list")
    @ApiOperation("获得位号数值列表")
    @ApiImplicitParam(name = "deviceId", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    //@PreAuthorize("@ss.hasPermission('system:point-info:query')")

    public CommonResult<List<PointValueRespVO>> getPointValueList(Long deviceId) {
        List<PointValue> list = pointValueService.latest(deviceId);
        return success(PointValueConvert.INSTANCE.convertList(list));

    }




}
