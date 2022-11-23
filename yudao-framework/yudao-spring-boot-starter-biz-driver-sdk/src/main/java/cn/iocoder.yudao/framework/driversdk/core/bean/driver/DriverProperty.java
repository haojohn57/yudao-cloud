package cn.iocoder.yudao.framework.driversdk.core.bean.driver;

import cn.iocoder.yudao.framework.common.enums.DriverConstants;
import cn.iocoder.yudao.framework.common.iot.model.DriverAttribute;
import cn.iocoder.yudao.framework.common.iot.model.PointAttribute;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * 驱动配置文件 driver 字段内容
 *
 * @author 益莲科技
 */
@Setter
@Getter
@Validated
@ConfigurationProperties(prefix = "driver")
public class DriverProperty {
    @NotBlank(message = "name can't be empty")
    @Pattern(
            regexp = "^[A-Za-z0-9\\u4e00-\\u9fa5][A-Za-z0-9\\u4e00-\\u9fa5-_#@/\\.\\|]{1,31}$",
            message = "Invalid name,contains invalid characters or length is not in the range of 2~32")
    private String tenant;
    private String name;
    private String type = DriverConstants.Type.DRIVER;
    private String description;
    //private ScheduleProperty schedule;
    private List<DriverAttribute> driverAttribute;
    private List<PointAttribute> pointAttribute;
}
