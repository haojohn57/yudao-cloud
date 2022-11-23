package cn.iocoder.yudao.module.system.controller.admin.iot.profile.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

/**
* 设备模板 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class ProfileBaseVO {

    @ApiModelProperty(value = "模板名称", required = true)
    @NotNull(message = "模板名称不能为空")
    private String name;

    @ApiModelProperty(value = "公有/私有模板标识")
    private Integer share;

    @ApiModelProperty(value = "系统创建/用户创建/驱动创建")
    private Integer type;

    @ApiModelProperty(value = "是否可用")
    private Integer enable;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @ApiModelProperty(value = "设备ID")
    private Long deviceId;

    @ApiModelProperty(value = "租户ID")
    private Long tenantId;


    @ApiModelProperty(value = "描述")
    private String description;



}
