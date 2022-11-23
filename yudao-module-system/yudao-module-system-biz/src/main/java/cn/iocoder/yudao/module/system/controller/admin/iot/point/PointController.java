package cn.iocoder.yudao.module.system.controller.admin.iot.point;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.system.controller.admin.iot.point.vo.*;
import cn.iocoder.yudao.module.system.convert.iot.point.PointConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.point.PointDO;
import cn.iocoder.yudao.module.system.service.iot.point.PointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Api(tags = "管理后台 - 设备位号")
@RestController
@RequestMapping("/system/point")
@Validated
public class PointController {

    @Resource
    private PointService pointService;

    @PostMapping("/create")
    @ApiOperation("创建设备位号")
    @PreAuthorize("@ss.hasPermission('system:point:create')")
    public CommonResult<Long> createPoint(@Valid @RequestBody PointCreateReqVO createReqVO) {
        return success(pointService.createPoint(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新设备位号")
    @PreAuthorize("@ss.hasPermission('system:point:update')")
    public CommonResult<Boolean> updatePoint(@Valid @RequestBody PointUpdateReqVO updateReqVO) {
        pointService.updatePoint(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除设备位号")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:point:delete')")
    public CommonResult<Boolean> deletePoint(@RequestParam("id") Long id) {
        pointService.deletePoint(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得设备位号")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:point:query')")
    public CommonResult<PointRespVO> getPoint(@RequestParam("id") Long id) {
        PointDO point = pointService.getPoint(id);
        return success(PointConvert.INSTANCE.convert(point));
    }

    @GetMapping("/list")
    @ApiOperation("获得设备位号列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('system:point:query')")
    public CommonResult<List<PointRespVO>> getPointList(@RequestParam("ids") Collection<Long> ids) {
        List<PointDO> list = pointService.getPointList(ids);
        return success(PointConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得设备位号分页")
    @PreAuthorize("@ss.hasPermission('system:point:query')")
    public CommonResult<PageResult<PointRespVO>> getPointPage(@Valid PointPageReqVO pageVO) {
        PageResult<PointDO> pageResult = pointService.getPointPage(pageVO);
        return success(PointConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出设备位号 Excel")
    @PreAuthorize("@ss.hasPermission('system:point:export')")
    @OperateLog(type = EXPORT)
    public void exportPointExcel(@Valid PointExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<PointDO> list = pointService.getPointList(exportReqVO);
        // 导出 Excel
        List<PointExcelVO> datas = PointConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "设备位号.xls", "数据", PointExcelVO.class, datas);
    }

}
