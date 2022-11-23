package cn.iocoder.yudao.module.system.controller.admin.iot.profile.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel(value = "管理后台 - 设备模板 Excel 导出 Request VO", description = "参数和 ProfilePageReqVO 是一致的")
@Data
public class ProfileExportReqVO {

    @ApiModelProperty(value = "模板名称")
    private String name;

    @ApiModelProperty(value = "公有/私有模板标识")
    private Integer share;

    @ApiModelProperty(value = "系统创建/用户创建/驱动创建")
    private Integer type;

    @ApiModelProperty(value = "是否可用")
    private Integer enable;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @ApiModelProperty(value = "设备ID")
    private Long deviceId;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;

}
