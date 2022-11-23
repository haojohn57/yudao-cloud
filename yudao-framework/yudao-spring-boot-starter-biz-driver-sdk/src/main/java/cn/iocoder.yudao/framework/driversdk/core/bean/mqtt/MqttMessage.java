package cn.iocoder.yudao.framework.driversdk.core.bean.mqtt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author 益莲科技
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MqttMessage implements Serializable {
    private MessageHeader messageHeader;
    private MessagePayload messagePayload;
}
