package cn.iocoder.yudao.framework.driversdk.core.service.impl;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.enums.DeviceConstants;
import cn.iocoder.yudao.framework.common.enums.ValueConstants;
import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.iot.bean.point.PointValue;
import cn.iocoder.yudao.framework.common.iot.model.DeviceEvent;
import cn.iocoder.yudao.framework.common.iot.model.DriverEvent;
import cn.iocoder.yudao.framework.common.iot.model.Point;
import cn.iocoder.yudao.framework.driversdk.core.bean.driver.DriverContext;
import cn.iocoder.yudao.framework.driversdk.core.service.DriverServiceSdk;
import cn.iocoder.yudao.framework.driversdk.core.service.mq.producer.DeviceProducer;
import cn.iocoder.yudao.framework.driversdk.core.service.mq.producer.DriverSdkProducer;
import cn.iocoder.yudao.framework.driversdk.core.service.mq.producer.PointProducer;
import cn.iocoder.yudao.framework.driversdk.core.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 益莲科技
 */
@Slf4j
@Service
public class DriverServiceSdkImpl implements DriverServiceSdk {

    @Value("${spring.application.name}")
    private String serviceName;

    @Resource
    private DriverContext driverContext;
    @Resource
    private DriverSdkProducer driverSdkProducer;
    @Resource
    private DeviceProducer deviceProducer;
    @Resource
    private PointProducer pointProducer;
    @Resource
    private ApplicationContext applicationContext;

    public String convertValue(Long deviceId, Long pointId, String rawValue) {
        String value;
        Point point = driverContext.getPointByDeviceIdAndPointId(deviceId, pointId);
        switch (point.getType()) {
            case ValueConstants.Type.STRING:
                value = rawValue;
                break;
            case ValueConstants.Type.BYTE:
            case ValueConstants.Type.SHORT:
            case ValueConstants.Type.INT:
            case ValueConstants.Type.LONG:
            case ValueConstants.Type.DOUBLE:
            case ValueConstants.Type.FLOAT:
                try {
                    float base = null != point.getBase() ? point.getBase() : 0;
                    float multiple = null != point.getMultiple() ? point.getMultiple() : 1;
                    double temp = (Convert.convert(Double.class, rawValue.trim()) + base) * multiple;
                    if (null != point.getMinimum() && temp < point.getMinimum()) {
                        log.info("Device({}) point({}) value({}) is lower than lower limit({})", deviceId, pointId, temp, point.getMinimum());
                        deviceEventSender(deviceId, pointId, DeviceConstants.Event.OVER_LOWER_LIMIT,
                                String.format("Value(%s) is lower than lower limit %s", temp, point.getMinimum()));
                    }
                    if (null != point.getMaximum() && temp > point.getMaximum()) {
                        log.info("Device({}) point({}) value({}) is greater than upper limit({})", deviceId, pointId, temp, point.getMaximum());
                        deviceEventSender(deviceId, pointId, DeviceConstants.Event.OVER_UPPER_LIMIT,
                                String.format("Value(%s) is greater than upper limit %s", temp, point.getMaximum()));
                    }
                    if (StrUtil.isNotBlank(point.getFormat())) {
                        value = String.format(point.getFormat(), temp);
                    } else {
                        value = String.valueOf(temp);
                    }
                } catch (Exception e) {
                    throw new ServiceException(10000,e.getMessage());
                }
                break;
            case ValueConstants.Type.BOOLEAN:
                try {
                    try {
                        Double booleanValue = Convert.convert(Double.class, rawValue.trim());
                        if (booleanValue > 0) {
                            value = Boolean.TRUE.toString();
                        } else {
                            value = Boolean.FALSE.toString();
                        }
                    } catch (Exception e) {
                        value = String.valueOf(Boolean.parseBoolean(rawValue.trim()));
                    }
                } catch (Exception e) {
                    throw new ServiceException(100000, e.getMessage());
                }
                break;
            default:
                throw new ServiceException(100000,point.getType());
        }

        return value;
    }

    @Override
    public void driverEventSender(DriverEvent driverEvent) {
        if (null != driverEvent) {
            log.debug("Send driver event: {}", JsonUtil.toJsonString(driverEvent));
            driverSdkProducer.sendDriverEvent(driverEvent);
        }
    }

    public void deviceEventSender(DeviceEvent deviceEvent) {
        if (null != deviceEvent) {
            log.debug("Send device event: {}", JsonUtil.toJsonString(deviceEvent));
            deviceProducer.sendDeviceEvent(deviceEvent);
        }
    }

    public void deviceEventSender(Long deviceId, String type, String content) {
        deviceEventSender(new DeviceEvent(deviceId, type, content));
    }

    public void deviceEventSender(Long deviceId, Long pointId, String type, String content) {
        deviceEventSender(new DeviceEvent(deviceId, pointId, type, content));
    }

    public void pointValueSender(PointValue pointValue) {
        if (null != pointValue) {
            log.debug("Send point value: {}", JsonUtil.toJsonString(pointValue));
            pointProducer.sendPoint(pointValue);
        }
    }

    public void pointValueSender(List<PointValue> pointValues) {
        // TODO 需要添加新的队列支持list数据发送
        if (null != pointValues) {
            pointValues.forEach(this::pointValueSender);
        }
    }

    public void close(CharSequence template, Object... params) {
        log.error(StrUtil.format(template, params));
        ((ConfigurableApplicationContext) applicationContext).close();
        System.exit(1);
    }

}
