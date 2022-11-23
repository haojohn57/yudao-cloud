package cn.iocoder.yudao.framework.common.iot.bean.driver.command;

import cn.iocoder.yudao.framework.common.validation.Read;
import cn.iocoder.yudao.framework.common.validation.Write;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 益莲科技
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CmdParameter {
    @NotNull(message = "device id can't be empty", groups = {Read.class, Write.class})
    private Long deviceId;

    @NotNull(message = "point id can't be empty", groups = {Read.class, Write.class})
    private Long pointId;

    @NotBlank(message = "value can't be empty", groups = {Write.class})
    private String value;
}
