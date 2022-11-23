package cn.iocoder.yudao.module.system.controller.admin.iot.point.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@ApiModel("管理后台 - 设备位号分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PointPageReqVO extends PageParam {

    @ApiModelProperty(value = "位号名称")
    private String name;

    @ApiModelProperty(value = "数据类型：stringintdoublefloatlongoolean")
    private String type;

    @ApiModelProperty(value = "读写标识：0读，1写，2读写")
    private Integer rw;

    @ApiModelProperty(value = "基础值")
    private Float base;

    @ApiModelProperty(value = "最小值")
    private Float minimum;

    @ApiModelProperty(value = "最大值")
    private Float maximum;

    @ApiModelProperty(value = "倍数")
    private Float multiple;

    @ApiModelProperty(value = "累计标识")
    private Integer accrue;

    @ApiModelProperty(value = "格式数据，Jave格式 %.3f")
    private String format;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "是否可用")
    private Integer enable;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @ApiModelProperty(value = "模板ID")
    private Long profileId;

    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Date[] createTime;

}
