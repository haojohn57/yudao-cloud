package cn.iocoder.yudao.module.system.controller.admin.iot.driverinfo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel(value = "管理后台 - 协议配置信息 Excel 导出 Request VO", description = "参数和 DriverInfoPageReqVO 是一致的")
@Data
public class DriverInfoExportReqVO {

    @ApiModelProperty(value = "连接配置ID")
    private Long driverAttributeId;

    @ApiModelProperty(value = "值")
    private String value;

    @ApiModelProperty(value = "设备ID")
    private Long deviceId;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;

}
