package cn.iocoder.yudao.module.system.controller.admin.iot.driverinfo;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverinfo.vo.*;
import cn.iocoder.yudao.module.system.convert.iot.driver.DriverInfoConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.driver.DriverInfoDO;
import cn.iocoder.yudao.module.system.service.iot.driverinfo.DriverInfoService;
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

@Api(tags = "管理后台 - 协议配置信息")
@RestController
@RequestMapping("/system/driver-info")
@Validated
public class DriverInfoController {

    @Resource
    private DriverInfoService driverInfoService;

    @PostMapping("/create")
    @ApiOperation("创建协议配置信息")
    @PreAuthorize("@ss.hasPermission('system:driver-info:create')")
    public CommonResult<Long> createDriverInfo(@Valid @RequestBody DriverInfoCreateReqVO createReqVO) {
        return success(driverInfoService.createDriverInfo(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新协议配置信息")
    @PreAuthorize("@ss.hasPermission('system:driver-info:update')")
    public CommonResult<Boolean> updateDriverInfo(@Valid @RequestBody DriverInfoUpdateReqVO updateReqVO) {
        driverInfoService.updateDriverInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除协议配置信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:driver-info:delete')")
    public CommonResult<Boolean> deleteDriverInfo(@RequestParam("id") Long id) {
        driverInfoService.deleteDriverInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得协议配置信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:driver-info:query')")
    public CommonResult<DriverInfoRespVO> getDriverInfo(@RequestParam("id") Long id) {
        DriverInfoDO driverInfo = driverInfoService.getDriverInfo(id);
        return success(DriverInfoConvert.INSTANCE.convert(driverInfo));
    }

    @GetMapping("/list")
    @ApiOperation("获得协议配置信息列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('system:driver-info:query')")
    public CommonResult<List<DriverInfoRespVO>> getDriverInfoList(@RequestParam("ids") Collection<Long> ids) {
        List<DriverInfoDO> list = driverInfoService.getDriverInfoList(ids);
        return success(DriverInfoConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得协议配置信息分页")
    @PreAuthorize("@ss.hasPermission('system:driver-info:query')")
    public CommonResult<PageResult<DriverInfoRespVO>> getDriverInfoPage(@Valid DriverInfoPageReqVO pageVO) {
        PageResult<DriverInfoDO> pageResult = driverInfoService.getDriverInfoPage(pageVO);
        return success(DriverInfoConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出协议配置信息 Excel")
    @PreAuthorize("@ss.hasPermission('system:driver-info:export')")
    @OperateLog(type = EXPORT)
    public void exportDriverInfoExcel(@Valid DriverInfoExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<DriverInfoDO> list = driverInfoService.getDriverInfoList(exportReqVO);
        // 导出 Excel
        List<DriverInfoExcelVO> datas = DriverInfoConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "协议配置信息.xls", "数据", DriverInfoExcelVO.class, datas);
    }

}
