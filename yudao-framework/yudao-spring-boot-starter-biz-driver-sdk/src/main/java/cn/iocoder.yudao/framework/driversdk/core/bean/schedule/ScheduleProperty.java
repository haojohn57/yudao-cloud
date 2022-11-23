package cn.iocoder.yudao.framework.driversdk.core.bean.schedule;

import lombok.Getter;
import lombok.Setter;

/**
 * 驱动配置文件 driver.schedule 字段内容
 *
 * @author 益莲科技
 */
@Setter
@Getter
public class ScheduleProperty {
    private ScheduleConfig read;
    private ScheduleConfig custom;
    private ScheduleConfig status;
}
