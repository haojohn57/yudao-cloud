package cn.iocoder.yudao.module.system.controller.admin.iot.device.vo;

import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

import javax.swing.*;
import javax.validation.constraints.*;

/**
* 设备 Base VO，提供给添加、修改、详细的子 VO 使用
* 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
*/
@Data
public class DeviceBaseVO {

    @ApiModelProperty(value = "设备名称", required = true, example = "汽车1")
    @NotNull(message = "设备名称不能为空")
    private String name;

    @ApiModelProperty(value = "位号数据是否结构化")
    private Integer multi;

    @ApiModelProperty(value = "设备状态", required = true)
    @NotNull(message = "设备状态不能为空")
    private Integer status;

    @ApiModelProperty(value = "设备分类", required = true, example = "电梯")
    @NotNull(message = "设备分类不能为空")
    private Integer category;

    @ApiModelProperty(value = "驱动ID", required = true)
    @NotNull(message = "驱动ID不能为空")
    private Long driverId;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    @ApiModelProperty(value = "描述")
    private String description;

}
