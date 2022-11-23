package cn.iocoder.yudao.module.system.controller.admin.iot.driverattribute;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverattribute.vo.*;
import cn.iocoder.yudao.module.system.convert.iot.driver.DriverAttributeConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.driver.DriverAttributeDO;
import cn.iocoder.yudao.module.system.service.iot.driverattribute.DriverAttributeService;
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

@Api(tags = "管理后台 - 连接配置信息")
@RestController
@RequestMapping("/system/driver-attribute")
@Validated
public class DriverAttributeController {

    @Resource
    private DriverAttributeService driverAttributeService;

    @PostMapping("/create")
    @ApiOperation("创建连接配置信息")
    @PreAuthorize("@ss.hasPermission('system:driver-attribute:create')")
    public CommonResult<Long> createDriverAttribute(@Valid @RequestBody DriverAttributeCreateReqVO createReqVO) {
        return success(driverAttributeService.createDriverAttribute(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新连接配置信息")
    @PreAuthorize("@ss.hasPermission('system:driver-attribute:update')")
    public CommonResult<Boolean> updateDriverAttribute(@Valid @RequestBody DriverAttributeUpdateReqVO updateReqVO) {
        driverAttributeService.updateDriverAttribute(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除连接配置信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:driver-attribute:delete')")
    public CommonResult<Boolean> deleteDriverAttribute(@RequestParam("id") Long id) {
        driverAttributeService.deleteDriverAttribute(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得连接配置信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:driver-attribute:query')")
    public CommonResult<DriverAttributeRespVO> getDriverAttribute(@RequestParam("id") Long id) {
        DriverAttributeDO driverAttribute = driverAttributeService.getDriverAttribute(id);
        return success(DriverAttributeConvert.INSTANCE.convert(driverAttribute));
    }

    @GetMapping("/list")
    @ApiOperation("获得连接配置信息列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('system:driver-attribute:query')")
    public CommonResult<List<DriverAttributeRespVO>> getDriverAttributeList(@RequestParam("ids") Collection<Long> ids) {
        List<DriverAttributeDO> list = driverAttributeService.getDriverAttributeList(ids);
        return success(DriverAttributeConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得连接配置信息分页")
    @PreAuthorize("@ss.hasPermission('system:driver-attribute:query')")
    public CommonResult<PageResult<DriverAttributeRespVO>> getDriverAttributePage(@Valid DriverAttributePageReqVO pageVO) {
        PageResult<DriverAttributeDO> pageResult = driverAttributeService.getDriverAttributePage(pageVO);
        return success(DriverAttributeConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出连接配置信息 Excel")
    @PreAuthorize("@ss.hasPermission('system:driver-attribute:export')")
    @OperateLog(type = EXPORT)
    public void exportDriverAttributeExcel(@Valid DriverAttributeExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<DriverAttributeDO> list = driverAttributeService.getDriverAttributeList(exportReqVO);
        // 导出 Excel
        List<DriverAttributeExcelVO> datas = DriverAttributeConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "连接配置信息.xls", "数据", DriverAttributeExcelVO.class, datas);
    }

}
