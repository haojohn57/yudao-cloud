package cn.iocoder.yudao.module.system.controller.admin.iot.driver;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.system.controller.admin.iot.driver.vo.*;
import cn.iocoder.yudao.module.system.convert.iot.driver.DriverConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.driver.DriverDO;
import cn.iocoder.yudao.module.system.service.iot.driver.DriverService;
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

@Api(tags = "管理后台 - 协议驱动")
@RestController
@RequestMapping("/system/driver")
@Validated
public class DriverController {

    @Resource
    private DriverService driverService;

    @PostMapping("/create")
    @ApiOperation("创建协议驱动")
    @PreAuthorize("@ss.hasPermission('system:driver:create')")
    public CommonResult<Long> createDriver(@Valid @RequestBody DriverCreateReqVO createReqVO) {
        return success(driverService.createDriver(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新协议驱动")
    @PreAuthorize("@ss.hasPermission('system:driver:update')")
    public CommonResult<Boolean> updateDriver(@Valid @RequestBody DriverUpdateReqVO updateReqVO) {
        driverService.updateDriver(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除协议驱动")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:driver:delete')")
    public CommonResult<Boolean> deleteDriver(@RequestParam("id") Long id) {
        driverService.deleteDriver(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得协议驱动")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:driver:query')")
    public CommonResult<DriverRespVO> getDriver(@RequestParam("id") Long id) {
        DriverDO driver = driverService.getDriver(id);
        return success(DriverConvert.INSTANCE.convert(driver));
    }

    @GetMapping("/list")
    @ApiOperation("获得协议驱动列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('system:driver:query')")
    public CommonResult<List<DriverRespVO>> getDriverList(@RequestParam("ids") Collection<Long> ids) {
        List<DriverDO> list = driverService.getDriverList(ids);
        return success(DriverConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得协议驱动分页")
    @PreAuthorize("@ss.hasPermission('system:driver:query')")
    public CommonResult<PageResult<DriverRespVO>> getDriverPage(@Valid DriverPageReqVO pageVO) {
        PageResult<DriverDO> pageResult = driverService.getDriverPage(pageVO);
        return success(DriverConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出协议驱动 Excel")
    @PreAuthorize("@ss.hasPermission('system:driver:export')")
    @OperateLog(type = EXPORT)
    public void exportDriverExcel(@Valid DriverExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<DriverDO> list = driverService.getDriverList(exportReqVO);
        // 导出 Excel
        List<DriverExcelVO> datas = DriverConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "协议驱动.xls", "数据", DriverExcelVO.class, datas);
    }

}
