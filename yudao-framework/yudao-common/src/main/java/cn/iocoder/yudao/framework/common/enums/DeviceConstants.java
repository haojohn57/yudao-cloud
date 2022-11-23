package cn.iocoder.yudao.framework.common.enums;

/**
 * Device 相关的枚举
 *
 *
 * @author 益莲科技
 */
public class DeviceConstants {

    /**
     * 设备事件
     */
    public interface Event {
        /**
         * 设备心跳事件
         */
        String HEARTBEAT = "heartbeat";

        /**
         * 超出上限事件
         */
        String OVER_UPPER_LIMIT = "over_upper_limit";

        /**
         * 超出下限事件
         */
        String OVER_LOWER_LIMIT = "over_lower_limit";

        /**
         * 用于记录错误事件类型
         */
        String ERROR = "error";
    }

}
