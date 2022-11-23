package cn.iocoder.yudao.module.system.controller.admin.iot.profile;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.system.controller.admin.iot.profile.vo.*;
import cn.iocoder.yudao.module.system.convert.iot.profile.ProfileConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.profile.ProfileDO;
import cn.iocoder.yudao.module.system.service.iot.profile.ProfileService;
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

@Api(tags = "管理后台 - 设备模板")
@RestController
@RequestMapping("/system/profile")
@Validated
public class ProfileController {

    @Resource
    private ProfileService profileService;

    @PostMapping("/create")
    @ApiOperation("创建设备模板")
    @PreAuthorize("@ss.hasPermission('system:profile:create')")
    public CommonResult<Long> createProfile(@Valid @RequestBody ProfileCreateReqVO createReqVO) {
        return success(profileService.createProfile(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新设备模板")
    @PreAuthorize("@ss.hasPermission('system:profile:update')")
    public CommonResult<Boolean> updateProfile(@Valid @RequestBody ProfileUpdateReqVO updateReqVO) {
        profileService.updateProfile(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除设备模板")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:profile:delete')")
    public CommonResult<Boolean> deleteProfile(@RequestParam("id") Long id) {
        profileService.deleteProfile(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得设备模板")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:profile:query')")
    public CommonResult<ProfileRespVO> getProfile(@RequestParam("id") Long id) {
        ProfileDO profile = profileService.getProfile(id);
        return success(ProfileConvert.INSTANCE.convert(profile));
    }

    @GetMapping("/list")
    @ApiOperation("获得设备模板列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('system:profile:query')")
    public CommonResult<List<ProfileRespVO>> getProfileList(@RequestParam("ids") Collection<Long> ids) {
        List<ProfileDO> list = profileService.getProfileList(ids);
        return success(ProfileConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得设备模板分页")
    @PreAuthorize("@ss.hasPermission('system:profile:query')")
    public CommonResult<PageResult<ProfileRespVO>> getProfilePage(@Valid ProfilePageReqVO pageVO) {
        PageResult<ProfileDO> pageResult = profileService.getProfilePage(pageVO);
        return success(ProfileConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出设备模板 Excel")
    @PreAuthorize("@ss.hasPermission('system:profile:export')")
    @OperateLog(type = EXPORT)
    public void exportProfileExcel(@Valid ProfileExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<ProfileDO> list = profileService.getProfileList(exportReqVO);
        // 导出 Excel
        List<ProfileExcelVO> datas = ProfileConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "设备模板.xls", "数据", ProfileExcelVO.class, datas);
    }

}
