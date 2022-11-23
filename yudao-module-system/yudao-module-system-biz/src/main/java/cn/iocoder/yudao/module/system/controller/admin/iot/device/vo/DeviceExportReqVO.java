package cn.iocoder.yudao.module.system.controller.admin.iot.device.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel(value = "管理后台 - 设备 Excel 导出 Request VO", description = "参数和 DevicePageReqVO 是一致的")
@Data
public class DeviceExportReqVO {

    @ApiModelProperty(value = "设备名称", example = "汽车1")
    private String name;

    @ApiModelProperty(value = "位号数据是否结构化")
    private Integer multi;

    @ApiModelProperty(value = "设备状态")
    private Integer status;

    @ApiModelProperty(value = "设备分类", example = "电梯")
    private Integer category;

    @ApiModelProperty(value = "驱动ID")
    private Long driverId;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;

}
