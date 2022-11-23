package cn.iocoder.yudao.framework.common.iot.bean.point;

import cn.iocoder.yudao.framework.common.validation.Insert;
import cn.iocoder.yudao.framework.common.validation.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 设备位号详情
 *
 * @author 益莲科技
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
public class PointDetail {

    @NotBlank(message = "device name can't be empty", groups = {Insert.class})
    @Pattern(regexp = "^[A-Za-z0-9\\u4e00-\\u9fa5][A-Za-z0-9\\u4e00-\\u9fa5-_#@/\\.\\|]{1,31}$", message = "Invalid device name,contains invalid characters or length is not in the range of 2~32", groups = {Insert.class, Update.class})
    private String deviceName;

    @NotBlank(message = "point name can't be empty", groups = {Insert.class})
    @Pattern(regexp = "^[A-Za-z0-9\\u4e00-\\u9fa5][A-Za-z0-9\\u4e00-\\u9fa5-_#@/\\.\\|]{1,31}$", message = "Invalid point name,contains invalid characters or length is not in the range of 2~32", groups = {Insert.class, Update.class})
    private String pointName;

    @NotNull(message = "driver id can't be empty", groups = {Insert.class, Update.class})
    private String driverId;

    private Long deviceId;
    private Long pointId;

    public PointDetail(Long deviceId, Long pointId) {
        this.deviceId = deviceId;
        this.pointId = pointId;
    }
}
