package cn.iocoder.yudao.framework.common.iot.model;

import cn.iocoder.yudao.framework.common.validation.Insert;
import cn.iocoder.yudao.framework.common.validation.Update;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * 设备与模版继承关系表
 *
 * @author 益莲科技
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ProfileBind extends Description {

    @NotNull(message = "profile id can't be empty", groups = {Insert.class, Update.class})
    private Long profileId;

    @NotNull(message = "device id can't be empty", groups = {Insert.class, Update.class})
    private Long deviceId;
}
