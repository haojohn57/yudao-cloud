package cn.iocoder.yudao.module.system.controller.admin.iot.profilebind.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel(value = "管理后台 - 设备与模版映射关联 Excel 导出 Request VO", description = "参数和 ProfileBindPageReqVO 是一致的")
@Data
public class ProfileBindExportReqVO {

    @ApiModelProperty(value = "模版ID")
    private Long profileId;

    @ApiModelProperty(value = "设备ID")
    private Long deviceId;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;

}
