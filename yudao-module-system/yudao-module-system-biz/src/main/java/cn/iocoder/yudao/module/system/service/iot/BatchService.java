package cn.iocoder.yudao.module.system.service.iot;

import cn.iocoder.yudao.framework.common.iot.bean.batch.BatchDriver;
import cn.iocoder.yudao.framework.common.iot.bean.driver.DriverMetadata;

import java.util.List;

/**
 * BatchService Interface
 *
 * @author 益莲科技
 */
public interface BatchService {

    /**
     * 批量导入
     * <ul>
     *     <li>驱动</li>
     *     <li>模版</li>
     *     <li>驱动配置</li>
     *     <li>位号</li>
     *     <li>设备</li>
     *     <li>位号配置</li>
     * </ul>
     *
     * @param batchDrivers List<BatchDriver>
     */
    //void batchImport(List<BatchDriver> batchDrivers);

    /**
     * 批量导出
     * <ul>
     *     <li>驱动</li>
     *     <li>模版</li>
     *     <li>驱动配置</li>
     *     <li>位号</li>
     *     <li>设备</li>
     *     <li>位号配置</li>
     * </ul>
     *
     * @param serviceName Driver Service Name
     * @return BatchDriver
     */
    //BatchDriver batchExport(String serviceName);

    /**
     * 获取驱动元数据
     *
     * @param serviceName Driver Service Name
     * @return DriverMetadata
     */
    DriverMetadata batchDriverMetadata(String serviceName);

}
