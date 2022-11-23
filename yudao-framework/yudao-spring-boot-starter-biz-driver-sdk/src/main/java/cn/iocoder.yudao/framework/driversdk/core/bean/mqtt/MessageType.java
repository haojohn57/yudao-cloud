package cn.iocoder.yudao.framework.driversdk.core.bean.mqtt;

import lombok.NoArgsConstructor;

/**
 * @author 益莲科技
 */
@NoArgsConstructor
public enum MessageType {
    OPC_UA,
    OPC_DA,
    MODBUS,
    PLC,
    SERIAL,
    SOCKET,
    HEARTBEAT,
    DEFAULT
}