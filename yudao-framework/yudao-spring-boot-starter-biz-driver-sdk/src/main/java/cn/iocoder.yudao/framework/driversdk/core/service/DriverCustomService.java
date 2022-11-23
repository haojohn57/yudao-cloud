package cn.iocoder.yudao.framework.driversdk.core.service;

import cn.iocoder.yudao.framework.common.iot.bean.driver.AttributeInfo;
import cn.iocoder.yudao.framework.common.iot.model.Device;
import cn.iocoder.yudao.framework.common.iot.model.Point;

import java.util.Map;

/**
 * 自定义驱动接口，开发的自定义驱动需要实现 read 和 write 接口，可以参考以提供的驱动模块写法</p>
 *
 * <ol>
 * <li>{@link DriverCustomService#initial} 初始化操作，需要根据不同的驱动实现该功能</li>
 * <li>{@link DriverCustomService#read} 读操作，需要根据不同的驱动实现该功能</li>
 * <li>{@link DriverCustomService#write} 写操作，需要根据不同的驱动实现该功能</li>
 * </ol>
 *
 * @author 益莲科技
 */
public interface DriverCustomService {
    /**
     * Initial Driver
     */
    void initial();

    /**
     * Read Operation
     *
     * @param driverInfo Driver Attribute Info
     * @param pointInfo  Point Attribute Info
     * @param device     Device
     * @param point      Point
     * @return String Value
     * @throws Exception Exception
     */
    String read(Map<String, AttributeInfo> driverInfo, Map<String, AttributeInfo> pointInfo, Device device, Point point) throws Exception;

    /**
     * Write Operation
     *
     * @param driverInfo Driver Attribute Info
     * @param pointInfo  Point Attribute Info
     * @param device     Device
     * @param value      Value Attribute Info
     * @return Boolean Boolean
     * @throws Exception Exception
     */
    Boolean write(Map<String, AttributeInfo> driverInfo, Map<String, AttributeInfo> pointInfo, Device device, AttributeInfo value) throws Exception;
    /**
     * Schedule Operation
     */
    void schedule();
}
