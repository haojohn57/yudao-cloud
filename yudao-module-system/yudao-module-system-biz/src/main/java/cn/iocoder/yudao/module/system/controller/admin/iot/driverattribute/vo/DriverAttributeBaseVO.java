package cn.iocoder.yudao.module.system.controller.admin.iot.driverattribute.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

/**
* 连接配置信息 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class DriverAttributeBaseVO {

    @ApiModelProperty(value = "显示名称", required = true)
    @NotNull(message = "显示名称不能为空")
    private String displayName;

    @ApiModelProperty(value = "名称", required = true)
    @NotNull(message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "类型", required = true)
    @NotNull(message = "类型不能为空")
    private String type;

    @ApiModelProperty(value = "默认值", required = true)
    @NotNull(message = "默认值不能为空")
    private String value;

    @ApiModelProperty(value = "驱动ID", required = true)
    @NotNull(message = "驱动ID不能为空")
    private Long driverId;

    @ApiModelProperty(value = "描述")
    private String description;

}
