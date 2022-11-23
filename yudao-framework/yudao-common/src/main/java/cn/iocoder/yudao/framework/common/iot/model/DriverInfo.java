package cn.iocoder.yudao.framework.common.iot.model;

import cn.iocoder.yudao.framework.common.validation.Insert;
import cn.iocoder.yudao.framework.common.validation.Update;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * 驱动配置信息表
 *
 * @author 益莲科技
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DriverInfo extends Description {

    @NotNull(message = "driver attribute id can't be empty", groups = {Insert.class, Update.class})
    private Long driverAttributeId;

    @NotNull(message = "driver attribute value can't be empty", groups = {Insert.class, Update.class})
    private String value;

    @NotNull(message = "device id can't be empty", groups = {Insert.class, Update.class})
    private Long deviceId;

}
