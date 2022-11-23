package cn.iocoder.yudao.framework.driversdk.core.bean.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 益莲科技
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleConfig {
    private Boolean enable = false;
    private String corn = "* */1 * * * ?";
}
