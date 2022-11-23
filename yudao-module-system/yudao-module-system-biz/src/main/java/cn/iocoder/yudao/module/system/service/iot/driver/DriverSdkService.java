package cn.iocoder.yudao.module.system.service.iot.driver;

import cn.iocoder.yudao.framework.common.iot.bean.driver.DriverRegister;

/**
 * Driver Sdk Interface
 *
 * @author 益莲科技
 */
public interface DriverSdkService {

    /**
     * 驱动注册
     *
     * @param driverRegister DriverRegister
     */
    void driverRegister(DriverRegister driverRegister);
}
