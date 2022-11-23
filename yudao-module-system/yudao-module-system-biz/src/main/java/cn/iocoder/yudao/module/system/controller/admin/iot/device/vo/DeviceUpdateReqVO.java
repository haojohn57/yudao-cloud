package cn.iocoder.yudao.module.system.controller.admin.iot.device.vo;

import lombok.*;
import io.swagger.annotations.*;
import javax.validation.constraints.*;

@ApiModel("管理后台 - 设备更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeviceUpdateReqVO extends DeviceBaseVO {

    @ApiModelProperty(value = "主键ID", required = true, example = "1024")
    @NotNull(message = "主键ID不能为空")
    private Long id;

}
