package cn.iocoder.yudao.module.driver.mqtt.bean;

import cn.iocoder.yudao.framework.common.util.json.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 益莲科技
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MqttPayload {
    private DataType dataType = DataType.DEFAULT;
    private String data;

    public MqttPayload(DataType dataType, Object target) {
        this.dataType = dataType;
        this.data = JsonUtils.toJsonString(target);
    }

    @NoArgsConstructor
    public enum DataType {
        OPC_UA, OPC_DA, MODBUS, PLC, SERIAL, SOCKET, HEARTBEAT, DEFAULT
    }
}
