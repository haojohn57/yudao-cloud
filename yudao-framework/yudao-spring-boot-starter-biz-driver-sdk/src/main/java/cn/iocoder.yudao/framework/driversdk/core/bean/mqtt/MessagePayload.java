package cn.iocoder.yudao.framework.driversdk.core.bean.mqtt;

import cn.iocoder.yudao.framework.common.util.json.JsonUtils;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 益莲科技
 */
@Data
@Accessors(chain = true)
public class MessagePayload {
    private String payload;
    private MessageType messageType;

    public MessagePayload() {
        this.messageType = MessageType.DEFAULT;
    }

    public MessagePayload(Object payload, MessageType messageType) {
        this.payload = JsonUtils.toJsonString(payload);
        this.messageType = messageType;
    }
}
