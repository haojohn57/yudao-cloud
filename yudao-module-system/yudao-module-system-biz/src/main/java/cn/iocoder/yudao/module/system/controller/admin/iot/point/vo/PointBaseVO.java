package cn.iocoder.yudao.module.system.controller.admin.iot.point.vo;

import cn.iocoder.yudao.framework.common.enums.ValueConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
* 设备位号 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class PointBaseVO {

    @ApiModelProperty(value = "位号名称", required = true)
    @NotNull(message = "位号名称不能为空")
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

    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    @ApiModelProperty(value = "描述")
    private String description;

    public PointBaseVO setDefault() {
        this.type = ValueConstants.Type.STRING;
        this.rw = 0;
        this.base = 0F;
        this.minimum = -999999F;
        this.maximum = 999999F;
        this.multiple = 1F;
        this.accrue = 1;
        this.format = "%3.f";
        this.unit = "\"";
        return this;
    }

}
