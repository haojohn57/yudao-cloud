package cn.iocoder.yudao.module.system.controller.admin.iot.pointinfo;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointinfo.vo.*;
import cn.iocoder.yudao.module.system.convert.iot.point.PointInfoConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.point.PointInfoDO;
import cn.iocoder.yudao.module.system.service.iot.pointinfo.PointInfoService;
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

@Api(tags = "管理后台 - 位号配置信息")
@RestController
@RequestMapping("/system/point-info")
@Validated
public class PointInfoController {

    @Resource
    private PointInfoService pointInfoService;

    @PostMapping("/create")
    @ApiOperation("创建位号配置信息")
    @PreAuthorize("@ss.hasPermission('system:point-info:create')")
    public CommonResult<Long> createPointInfo(@Valid @RequestBody PointInfoCreateReqVO createReqVO) {
        return success(pointInfoService.createPointInfo(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新位号配置信息")
    @PreAuthorize("@ss.hasPermission('system:point-info:update')")
    public CommonResult<Boolean> updatePointInfo(@Valid @RequestBody PointInfoUpdateReqVO updateReqVO) {
        pointInfoService.updatePointInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除位号配置信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:point-info:delete')")
    public CommonResult<Boolean> deletePointInfo(@RequestParam("id") Long id) {
        pointInfoService.deletePointInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得位号配置信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:point-info:query')")
    public CommonResult<PointInfoRespVO> getPointInfo(@RequestParam("id") Long id) {
        PointInfoDO pointInfo = pointInfoService.getPointInfo(id);
        return success(PointInfoConvert.INSTANCE.convert(pointInfo));
    }

    @GetMapping("/list")
    @ApiOperation("获得位号配置信息列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('system:point-info:query')")
    public CommonResult<List<PointInfoRespVO>> getPointInfoList(@RequestParam("ids") Collection<Long> ids) {
        List<PointInfoDO> list = pointInfoService.getPointInfoList(ids);
        return success(PointInfoConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得位号配置信息分页")
    @PreAuthorize("@ss.hasPermission('system:point-info:query')")
    public CommonResult<PageResult<PointInfoRespVO>> getPointInfoPage(@Valid PointInfoPageReqVO pageVO) {
        PageResult<PointInfoDO> pageResult = pointInfoService.getPointInfoPage(pageVO);
        return success(PointInfoConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出位号配置信息 Excel")
    @PreAuthorize("@ss.hasPermission('system:point-info:export')")
    @OperateLog(type = EXPORT)
    public void exportPointInfoExcel(@Valid PointInfoExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<PointInfoDO> list = pointInfoService.getPointInfoList(exportReqVO);
        // 导出 Excel
        List<PointInfoExcelVO> datas = PointInfoConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "位号配置信息.xls", "数据", PointInfoExcelVO.class, datas);
    }

}
