package cn.iocoder.yudao.module.system.controller.admin.iot.profilebind;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.system.controller.admin.iot.profilebind.vo.*;
import cn.iocoder.yudao.module.system.convert.iot.profile.ProfileBindConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.profile.ProfileBindDO;
import cn.iocoder.yudao.module.system.service.iot.profilebind.ProfileBindService;
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

@Api(tags = "管理后台 - 设备与模版映射关联")
@RestController
@RequestMapping("/system/profile-bind")
@Validated
public class ProfileBindController {

    @Resource
    private ProfileBindService profileBindService;

    @PostMapping("/create")
    @ApiOperation("创建设备与模版映射关联")
    @PreAuthorize("@ss.hasPermission('system:profile-bind:create')")
    public CommonResult<Long> createProfileBind(@Valid @RequestBody ProfileBindCreateReqVO createReqVO) {
        return success(profileBindService.createProfileBind(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新设备与模版映射关联")
    @PreAuthorize("@ss.hasPermission('system:profile-bind:update')")
    public CommonResult<Boolean> updateProfileBind(@Valid @RequestBody ProfileBindUpdateReqVO updateReqVO) {
        profileBindService.updateProfileBind(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除设备与模版映射关联")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:profile-bind:delete')")
    public CommonResult<Boolean> deleteProfileBind(@RequestParam("id") Long id) {
        profileBindService.deleteProfileBind(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得设备与模版映射关联")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermission('system:profile-bind:query')")
    public CommonResult<ProfileBindRespVO> getProfileBind(@RequestParam("id") Long id) {
        ProfileBindDO profileBind = profileBindService.getProfileBind(id);
        return success(ProfileBindConvert.INSTANCE.convert(profileBind));
    }

    @GetMapping("/list")
    @ApiOperation("获得设备与模版映射关联列表")
    @ApiImplicitParam(name = "ids", value = "编号列表", required = true, example = "1024,2048", dataTypeClass = List.class)
    @PreAuthorize("@ss.hasPermission('system:profile-bind:query')")
    public CommonResult<List<ProfileBindRespVO>> getProfileBindList(@RequestParam("ids") Collection<Long> ids) {
        List<ProfileBindDO> list = profileBindService.getProfileBindList(ids);
        return success(ProfileBindConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得设备与模版映射关联分页")
    @PreAuthorize("@ss.hasPermission('system:profile-bind:query')")
    public CommonResult<PageResult<ProfileBindRespVO>> getProfileBindPage(@Valid ProfileBindPageReqVO pageVO) {
        PageResult<ProfileBindDO> pageResult = profileBindService.getProfileBindPage(pageVO);
        return success(ProfileBindConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出设备与模版映射关联 Excel")
    @PreAuthorize("@ss.hasPermission('system:profile-bind:export')")
    @OperateLog(type = EXPORT)
    public void exportProfileBindExcel(@Valid ProfileBindExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<ProfileBindDO> list = profileBindService.getProfileBindList(exportReqVO);
        // 导出 Excel
        List<ProfileBindExcelVO> datas = ProfileBindConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "设备与模版映射关联.xls", "数据", ProfileBindExcelVO.class, datas);
    }

}
