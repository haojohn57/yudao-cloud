package cn.iocoder.yudao.framework.common.iot.model;

import cn.iocoder.yudao.framework.common.validation.Insert;
import cn.iocoder.yudao.framework.common.validation.Update;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * 位号配置信息表
 *
 * @author 益莲科技
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PointInfo extends Description {

    @NotNull(message = "point attribute id can't be empty", groups = {Insert.class, Update.class})
    private Long pointAttributeId;

    @NotNull(message = "point attribute value can't be empty", groups = {Insert.class, Update.class})
    private String value;

    @NotNull(message = "device id can't be empty", groups = {Insert.class, Update.class})
    private Long deviceId;

    @NotNull(message = "point id can't be empty", groups = {Insert.class, Update.class})
    private Long pointId;
}
