package cn.iocoder.yudao.module.system.controller.admin.iot.driver.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

/**
* 协议驱动 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class DriverBaseVO {

    @ApiModelProperty(value = "协议名称", required = true)
    @NotNull(message = "协议名称不能为空")
    private String name;

    @ApiModelProperty(value = "协议服务名称", required = true)
    @NotNull(message = "协议服务名称不能为空")
    private String serviceName;

    @ApiModelProperty(value = "类型，deriver、gateway...")
    private String type;

    @ApiModelProperty(value = "主机IP", required = true)
    @NotNull(message = "主机IP不能为空")
    private String host;

    @ApiModelProperty(value = "端口", required = true)
    @NotNull(message = "端口不能为空")
    private Integer port;

    @ApiModelProperty(value = "是否可用")
    private Integer enable;

    @ApiModelProperty(value = "描述")
    private String description;

}
