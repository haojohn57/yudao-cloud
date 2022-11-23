package cn.iocoder.yudao.module.system.controller.admin.iot.driver.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel(value = "管理后台 - 协议驱动 Excel 导出 Request VO", description = "参数和 DriverPageReqVO 是一致的")
@Data
public class DriverExportReqVO {

    @ApiModelProperty(value = "协议名称")
    private String name;

    @ApiModelProperty(value = "协议服务名称")
    private String serviceName;

    @ApiModelProperty(value = "类型，deriver、gateway...")
    private String type;

    @ApiModelProperty(value = "主机IP")
    private String host;

    @ApiModelProperty(value = "端口")
    private Integer port;

    @ApiModelProperty(value = "是否可用")
    private Integer enable;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;

}
