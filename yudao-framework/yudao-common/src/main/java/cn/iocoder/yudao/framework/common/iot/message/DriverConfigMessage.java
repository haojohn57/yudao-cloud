package cn.iocoder.yudao.framework.common.iot.message;

import cn.iocoder.yudao.framework.common.iot.bean.driver.DriverConfiguration;
import lombok.Data;

import javax.annotation.Resource;

/**
 * 驱动配置数据刷新 Message
 *
 * @author 益莲科技
 */
@Data
public class DriverConfigMessage {

    @Resource
    private DriverConfiguration driverConfiguration;
    public DriverConfigMessage() {
    }
    public DriverConfigMessage(DriverConfiguration driverConfiguration) {
        this.driverConfiguration = driverConfiguration;
    }

}
