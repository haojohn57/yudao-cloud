package cn.iocoder.yudao.module.system.controller.admin.iot.pointattribute.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel(value = "管理后台 - 位号配置信息 Excel 导出 Request VO", description = "参数和 PointAttributePageReqVO 是一致的")
@Data
public class PointAttributeExportReqVO {

    @ApiModelProperty(value = "显示名称")
    private String displayName;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "默认值")
    private String value;

    @ApiModelProperty(value = "驱动ID")
    private Long driverId;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;

}
