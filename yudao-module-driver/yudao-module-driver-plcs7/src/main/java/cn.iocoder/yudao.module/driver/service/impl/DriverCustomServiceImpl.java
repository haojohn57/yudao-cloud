package cn.iocoder.yudao.module.driver.service.impl;

import cn.iocoder.yudao.framework.common.enums.DeviceConstants;
import cn.iocoder.yudao.framework.common.enums.DriverConstants;
import cn.iocoder.yudao.framework.common.enums.ValueConstants;
import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.iot.bean.driver.AttributeInfo;
import cn.iocoder.yudao.framework.common.iot.model.Device;
import cn.iocoder.yudao.framework.common.iot.model.Point;
import cn.iocoder.yudao.framework.common.util.json.JsonUtils;
import cn.iocoder.yudao.framework.driversdk.core.bean.driver.DriverContext;
import cn.iocoder.yudao.framework.driversdk.core.service.DriverCustomService;
import cn.iocoder.yudao.framework.driversdk.core.service.DriverServiceSdk;
import cn.iocoder.yudao.module.driver.bean.PlcS7PointVariable;
import com.github.s7connector.api.S7Connector;
import com.github.s7connector.api.S7Serializer;
import com.github.s7connector.api.factory.S7ConnectorFactory;
import com.github.s7connector.api.factory.S7SerializerFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static cn.iocoder.yudao.framework.driversdk.core.utils.DriverUtil.attribute;
import static cn.iocoder.yudao.framework.driversdk.core.utils.DriverUtil.value;

/**
 * @author 益莲科技
 */
@Slf4j
@Service
public class DriverCustomServiceImpl implements DriverCustomService {

    @Resource
    private DriverContext driverContext;
    @Resource
    private DriverServiceSdk driverServiceSdk;

    /**
     * Plc Connector Map
     */
    private volatile Map<Long, MyS7Connector> s7ConnectorMap;

    @Data
    private static class MyS7Connector {
        private ReentrantReadWriteLock lock;
        private S7Connector connector;
    }

    @Override
    public void initial() {
        s7ConnectorMap = new ConcurrentHashMap<Long, MyS7Connector>(16);
    }

    @Override
    public String read(Map<String, AttributeInfo> driverInfo, Map<String, AttributeInfo> pointInfo, Device device, Point point) throws Exception {
        log.debug("Plc S7 Read, device: {}, point: {}", JsonUtils.toJsonString(device), JsonUtils.toJsonString(point));
        MyS7Connector myS7Connector = getS7Connector(device.getId(), driverInfo);
        myS7Connector.lock.writeLock().lock();
        S7Serializer serializer = S7SerializerFactory.buildSerializer(myS7Connector.getConnector());
        PlcS7PointVariable plcs7PointVariable = getPointVariable(pointInfo, point.getType());

        try {
            return String.valueOf(serializer.dispense(plcs7PointVariable));
        } catch (Exception e) {
            log.error("Plc S7 Read Error: {}", e.getMessage());
            return "nil";
        } finally {
            myS7Connector.lock.writeLock().unlock();
        }
    }

    @Override
    public Boolean write(Map<String, AttributeInfo> driverInfo, Map<String, AttributeInfo> pointInfo, Device device, AttributeInfo value) throws Exception {
        log.debug("Plc S7 Write, device: {}, value: {}", JsonUtils.toJsonString(device), JsonUtils.toJsonString(value));
        MyS7Connector myS7Connector = getS7Connector(device.getId(), driverInfo);
        myS7Connector.lock.writeLock().lock();
        S7Serializer serializer = S7SerializerFactory.buildSerializer(myS7Connector.getConnector());
        PlcS7PointVariable plcs7PointVariable = getPointVariable(pointInfo, value.getType());

        try {
            store(serializer, plcs7PointVariable, value.getType(), value.getValue());
            return true;
        } catch (Exception e) {
            log.error("Plc S7 Write Error: {}", e.getMessage());
            return false;
        } finally {
            myS7Connector.lock.writeLock().unlock();
        }
    }

    @Override
    public void schedule() {

        /*
        TODO:设备状态
        上传设备状态，可自行灵活拓展，不一定非要在schedule()接口中实现，也可以在read中实现设备状态的设置；
        你可以通过某种判断机制确定设备的状态，然后通过driverService.deviceEventSender接口将设备状态交给SDK管理。

        设备状态（DeviceStatus）如下：
        ONLINE:在线
        OFFLINE:离线
        MAINTAIN:维护
        FAULT:故障
         */
        driverContext.getDriverMetadata().getDeviceMap().keySet().forEach(id -> driverServiceSdk.deviceEventSender(id, DeviceConstants.Event.HEARTBEAT, DriverConstants.Status.ONLINE));
    }

    /**
     * 获取 plcs7 serializer
     * 先从缓存中取，没有就新建
     *
     * @param deviceId   Device Id
     * @param driverInfo DeviceInfo Map
     * @return S7Serializer
     */
    private MyS7Connector getS7Connector(Long deviceId, Map<String, AttributeInfo> driverInfo) {
        MyS7Connector myS7Connector = s7ConnectorMap.get(deviceId);
        if (null == myS7Connector) {
            myS7Connector = new MyS7Connector();

            log.debug("Plc S7 Connection Info {}", JsonUtils.toJsonString(driverInfo));
            try {
                S7Connector s7Connector = S7ConnectorFactory.buildTCPConnector()
                        .withHost(attribute(driverInfo, "host"))
                        .withPort(attribute(driverInfo, "port"))
                        .build();
                myS7Connector.setLock(new ReentrantReadWriteLock());
                myS7Connector.setConnector(s7Connector);
            } catch (Exception e) {
                throw new ServiceException();
            }
            s7ConnectorMap.put(deviceId, myS7Connector);
        }
        return myS7Connector;
    }

    /**
     * 获取位号变量信息
     *
     * @param pointInfo PointInfo Map
     * @return Plcs7PointVariable
     */
    private PlcS7PointVariable getPointVariable(Map<String, AttributeInfo> pointInfo, String type) {
        log.debug("Plc S7 Point Info {}", JsonUtils.toJsonString(pointInfo));
        return new PlcS7PointVariable(attribute(pointInfo, "dbNum"), attribute(pointInfo, "byteOffset"), attribute(pointInfo, "bitOffset"), attribute(pointInfo, "blockSize"), type);
    }

    /**
     * 向 Plc S7 写数据
     *
     * @param serializer         S7Serializer
     * @param plcS7PointVariable Plcs7PointVariable
     * @param type               Value Type
     * @param value              String Value
     */
    private void store(S7Serializer serializer, PlcS7PointVariable plcS7PointVariable, String type, String value) {
        switch (type.toLowerCase()) {
            case ValueConstants.Type.INT:
                int intValue = value(type, value);
                serializer.store(intValue, plcS7PointVariable.getDbNum(), plcS7PointVariable.getByteOffset());
                break;
            case ValueConstants.Type.LONG:
                long longValue = value(type, value);
                serializer.store(longValue, plcS7PointVariable.getDbNum(), plcS7PointVariable.getByteOffset());
                break;
            case ValueConstants.Type.FLOAT:
                float floatValue = value(type, value);
                serializer.store(floatValue, plcS7PointVariable.getDbNum(), plcS7PointVariable.getByteOffset());
                break;
            case ValueConstants.Type.DOUBLE:
                double doubleValue = value(type, value);
                serializer.store(doubleValue, plcS7PointVariable.getDbNum(), plcS7PointVariable.getByteOffset());
                break;
            case ValueConstants.Type.BOOLEAN:
                boolean booleanValue = value(type, value);
                serializer.store(booleanValue, plcS7PointVariable.getDbNum(), plcS7PointVariable.getByteOffset());
                break;
            case ValueConstants.Type.STRING:
                serializer.store(value, plcS7PointVariable.getDbNum(), plcS7PointVariable.getByteOffset());
                break;
            default:
                break;
        }
    }

}
