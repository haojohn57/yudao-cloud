package cn.iocoder.yudao.module.driver.service.impl;

import cn.iocoder.yudao.framework.common.enums.DeviceConstants;
import cn.iocoder.yudao.framework.common.enums.DriverConstants;
import cn.iocoder.yudao.framework.common.enums.ValueConstants;
import cn.iocoder.yudao.framework.common.iot.bean.driver.AttributeInfo;
import cn.iocoder.yudao.framework.common.iot.model.Device;
import cn.iocoder.yudao.framework.common.iot.model.Point;
import cn.iocoder.yudao.framework.common.validation.Update;
import cn.iocoder.yudao.framework.driversdk.core.bean.driver.DriverContext;
import cn.iocoder.yudao.framework.driversdk.core.service.DriverCustomService;
import cn.iocoder.yudao.framework.driversdk.core.service.DriverServiceSdk;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.identity.AnonymousProvider;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static cn.iocoder.yudao.framework.driversdk.core.utils.DriverUtil.attribute;
import static cn.iocoder.yudao.framework.driversdk.core.utils.DriverUtil.value;
import static org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

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

    private static Map<Long, OpcUaClient> clientMap = new ConcurrentHashMap<Long, OpcUaClient>(16);


    @Override
    public void initial() {
    }

    @Override
    public String read(Map<String, AttributeInfo> driverInfo, Map<String, AttributeInfo> pointInfo, Device device, Point point) throws Exception {
        int namespace = attribute(pointInfo, "namespace");
        String tag = attribute(pointInfo, "tag");

        NodeId nodeId = new NodeId(namespace, tag);
        CompletableFuture<String> value = new CompletableFuture<>();
        OpcUaClient client = getOpcUaClient(device.getId(), driverInfo);

        client.connect().get();
        client.readValue(0.0, TimestampsToReturn.Both, nodeId).thenAccept(dataValue -> {
            try {
                value.complete(dataValue.getValue().getValue().toString());
            } catch (Exception e) {
                log.error("accept point(ns={};s={}) value error: {}", namespace, tag, e.getMessage());
            }
        });
        String rawValue = value.get(1, TimeUnit.SECONDS);
        log.debug("read point(ns={};s={}) value: {}", namespace, tag, rawValue);
        return rawValue;

    }

    @Override
    public Boolean write(Map<String, AttributeInfo> driverInfo, Map<String, AttributeInfo> pointInfo, Device device, AttributeInfo value) throws Exception {
        writeItem(device.getId(), driverInfo, pointInfo, value);
        return true;
    }

    @Override
    public void schedule() {
        driverContext.getDriverMetadata().getDeviceMap().keySet().forEach(id -> driverServiceSdk.deviceEventSender(id, DeviceConstants.Event.HEARTBEAT, DriverConstants.Status.ONLINE));
    }

    /**
     * Get Opc Ua Client
     *
     * @param deviceId   Device Id
     * @param driverInfo Driver Info
     * @return OpcUaClient
     * @throws UaException UaException
     */
    private OpcUaClient getOpcUaClient(Long deviceId, Map<String, AttributeInfo> driverInfo) {
        OpcUaClient opcUaClient = clientMap.get(deviceId);
        if (null == opcUaClient) {
            try {
                opcUaClient = OpcUaClient.create(
                        String.format("opc.tcp://%s:%s%s",
                                attribute(driverInfo, "host"),
                                attribute(driverInfo, "port"),
                                attribute(driverInfo, "path")
                        ),
                        endpoints -> endpoints.stream().findFirst(),
                        configBuilder -> configBuilder
                                .setIdentityProvider(new AnonymousProvider())
                                .setRequestTimeout(uint(5000))
                                .build()
                );
                clientMap.put(deviceId, opcUaClient);
            } catch (UaException e) {
                log.error("get opc ua client error: {}", e.getMessage());
                clientMap.entrySet().removeIf(next -> next.getKey().equals(deviceId));
            }
        }
        return clientMap.get(deviceId);
    }

    /**
     * Write Opc Ua Point Value
     *
     * @param deviceId   Device Id
     * @param driverInfo Driver Info
     * @param pointInfo  Point Info
     * @param values     Value Array
     * @throws UaException          UaException
     * @throws ExecutionException   ExecutionException
     * @throws InterruptedException InterruptedException
     */
    private void writeItem(@NotNull(message = "Id can't be empty", groups = {Update.class}) Long deviceId, Map<String, AttributeInfo> driverInfo, Map<String, AttributeInfo> pointInfo, AttributeInfo values) {
        OpcUaClient client;
        try {
            int namespace = attribute(pointInfo, "namespace");
            String tag = attribute(pointInfo, "tag"), type = values.getType(), value = values.getValue();
            NodeId nodeId = new NodeId(namespace, tag);

            client = getOpcUaClient(deviceId, driverInfo);
            client.connect().get();

            switch (type.toLowerCase()) {
                case ValueConstants.Type.INT:
                    int intValue = value(type, value);
                    client.writeValue(nodeId, new DataValue(new Variant(intValue)));
                    break;
                case ValueConstants.Type.LONG:
                    long longValue = value(type, value);
                    client.writeValue(nodeId, new DataValue(new Variant(longValue)));
                    break;
                case ValueConstants.Type.FLOAT:
                    float floatValue = value(type, value);
                    client.writeValue(nodeId, new DataValue(new Variant(floatValue)));
                    break;
                case ValueConstants.Type.DOUBLE:
                    double doubleValue = value(type, value);
                    client.writeValue(nodeId, new DataValue(new Variant(doubleValue)));
                    break;
                case ValueConstants.Type.BOOLEAN:
                    boolean booleanValue = value(type, value);
                    client.writeValue(nodeId, new DataValue(new Variant(booleanValue)));
                    break;
                case ValueConstants.Type.STRING:
                    client.writeValue(nodeId, new DataValue(new Variant(value)));
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            log.error("Opc Ua Point Write Error: {}", e.getMessage());
        }
    }

}
