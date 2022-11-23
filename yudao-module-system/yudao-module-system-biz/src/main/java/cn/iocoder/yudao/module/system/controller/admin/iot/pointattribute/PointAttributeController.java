package cn.iocoder.yudao.module.system.controller.admin.iot.pointattribute;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointattribute.vo.*;
import cn.iocoder.yudao.module.system.convert.iot.point.PointAttributeConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.point.PointAttributeDO;
import cn.iocoder.yudao.module.system.service.iot.pointattribute.PointAttributeService;
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
@RequestMapping("/system/point-attribute")
@Validated
public class PointAttributeController {

    @Resource
    private PointAttributeService pointAttributeService;

    @PostMapping("/create")
    @ApiOperation("创建位号配置信息")
    @PreAuthorize("@ss.hasPermission('system:point-attribute:create')")
    public CommonResult<Long> createPointAttribute(@Valid @RequestBody PointAttributeCreateReqVO createReqVO) {
        return success(pointAttributeService.createPointAttribute(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新位号配置信息")
    @PreAuthorize("@ss.hasPermission('system:point-attribute:update')")
    public CommonResult<Boolean> updatePointAttribute(@Valid @RequestBody PointAttributeUpdateReqVO updateReqVO) {
        pointAttributeService.updatePointAttribute(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除位号配置信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:point-attribute:delete')")
    public CommonResult<Boolean> deletePointAttribute(@RequestParam("id") Long id) {
        pointAttributeService.deletePointAttribute(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得位号配置信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:point-attribute:query')")
    public CommonResult<PointAttributeRespVO> getPointAttribute(@RequestParam("id") Long id) {
        PointAttributeDO pointAttribute = pointAttributeService.getPointAttribute(id);
        return success(PointAttributeConvert.INSTANCE.convert(pointAttribute));
    }

    @GetMapping("/list")
    @ApiOperation("获得位号配置信息列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('system:point-attribute:query')")
    public CommonResult<List<PointAttributeRespVO>> getPointAttributeList(@RequestParam("ids") Collection<Long> ids) {
        List<PointAttributeDO> list = pointAttributeService.getPointAttributeList(ids);
        return success(PointAttributeConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得位号配置信息分页")
    @PreAuthorize("@ss.hasPermission('system:point-attribute:query')")
    public CommonResult<PageResult<PointAttributeRespVO>> getPointAttributePage(@Valid PointAttributePageReqVO pageVO) {
        PageResult<PointAttributeDO> pageResult = pointAttributeService.getPointAttributePage(pageVO);
        return success(PointAttributeConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出位号配置信息 Excel")
    @PreAuthorize("@ss.hasPermission('system:point-attribute:export')")
    @OperateLog(type = EXPORT)
    public void exportPointAttributeExcel(@Valid PointAttributeExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<PointAttributeDO> list = pointAttributeService.getPointAttributeList(exportReqVO);
        // 导出 Excel
        List<PointAttributeExcelVO> datas = PointAttributeConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "位号配置信息.xls", "数据", PointAttributeExcelVO.class, datas);
    }

}
