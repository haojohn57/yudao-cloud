package cn.iocoder.yudao.module.data.controller.admin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
* 位号数值信息 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class PointValueBaseVO {

    @ApiModelProperty(value = "位号配置ID", required = true)
    @NotNull(message = "位号配置ID不能为空")
    private Long pointAttributeId;

    @ApiModelProperty(value = "值", required = true)
    @NotNull(message = "值不能为空")
    private String value;

    @ApiModelProperty(value = "设备ID", required = true)
    @NotNull(message = "设备ID不能为空")
    private Long deviceId;

    @ApiModelProperty(value = "位号ID", required = true)
    @NotNull(message = "位号ID不能为空")
    private Long pointId;

    @ApiModelProperty(value = "描述")
    private String description;

}
