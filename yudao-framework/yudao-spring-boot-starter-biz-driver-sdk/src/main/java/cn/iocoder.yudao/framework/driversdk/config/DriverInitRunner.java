package cn.iocoder.yudao.framework.driversdk.config;

import cn.iocoder.yudao.framework.driversdk.core.bean.driver.DriverProperty;
import cn.iocoder.yudao.framework.driversdk.core.service.DriverCustomService;
import cn.iocoder.yudao.framework.driversdk.core.service.DriverMetadataService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Driver SDK Initial
 *
 * @author 益莲科技
 */
@Component
@ComponentScan(basePackages = {
        "cn.iocoder.yudao.framework.driversdk.core"
})
@EnableConfigurationProperties({DriverProperty.class})
public class DriverInitRunner implements ApplicationRunner {
    @Resource
    private DriverCustomService driverCustomService;
    @Resource
    private DriverMetadataService driverMetadataService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Initialize driver configuration
        driverMetadataService.initial();

        // Initialize custom driver service
        driverCustomService.initial();

    }
}
