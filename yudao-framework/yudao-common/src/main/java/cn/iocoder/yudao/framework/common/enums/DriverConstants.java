package cn.iocoder.yudao.framework.common.enums;

/**
 * Driver 相关的枚举
 *
 *
 * @author 益莲科技
 */
public class DriverConstants {

    public static final int DEFAULT_MAX_REQUEST_SIZE = 100;

    /**
     * 状态相关
     */
    public interface Status {
        /**
         * 注册状态相关
         */
        String REGISTERING = "REGISTERING";
        String UNREGISTERED = "UNREGISTERED";

        /**
         * 运行状态相关
         */
        String ONLINE = "ONLINE";
        String OFFLINE = "OFFLINE";
        String MAINTAIN = "MAINTAIN";
        String FAULT = "FAULT";
    }
    /**
     * 类型
     */
    public interface Type {
        String DRIVER = "driver";
        String GATEWAY = "gateway";
        String PROFILE = "profile";
        String DEVICE = "device";
        String POINT = "point";
        String DRIVER_INFO = "driver_info";
        String POINT_INFO = "point_info";
    }
    /**
     * 事件相关
     */
    public interface Event {
        /**
         * 驱动注册握手事件，该事件用于校验当前 dc3-center-manager 是否可用
         */
        String DRIVER_HANDSHAKE = "driver_handshake";
        String DRIVER_HANDSHAKE_BACK = "driver_handshake_back";

        /**
         * 驱动注册事件，该事件用于向 dc3-center-manager 注册驱动配置信息
         */
        String DRIVER_REGISTER = "driver_register";
        String DRIVER_REGISTER_BACK = "driver_register_back";

        /**
         * 同步驱动元数据时间，该事件用于向 dc3-center-manager 发送驱动元数据同步请求
         */
        String DRIVER_METADATA_SYNC = "driver_metadata_sync";
        String DRIVER_METADATA_SYNC_BACK = "driver_metadata_sync_back";

        /**
         * 驱动心跳事件，该事件用于向 dc3-center-manager 发送驱动的当前状态
         */
        String DRIVER_HEARTBEAT = "driver_heartbeat";

        String ERROR = "error";
    }

    /**
     * 响应相关
     */
    public interface Response {
        String OK = "ok";
        String ERROR = "error";
    }

    public interface Profile {
        String ADD = "add_profile";
        String DELETE = "delete_profile";
        String UPDATE = "update_profile";
    }

    public interface Device {
        String ADD = "add_device";
        String DELETE = "delete_device";
        String UPDATE = "update_device";
    }

    public interface Point {
        String ADD = "add_point";
        String DELETE = "delete_point";
        String UPDATE = "update_point";
    }

    public interface DriverInfo {
        String ADD = "add_driver_info";
        String DELETE = "delete_driver_info";
        String UPDATE = "update_driver_info";
    }

    public interface PointInfo {
        String ADD = "add_point_info";
        String DELETE = "delete_point_info";
        String UPDATE = "update_point_info";
    }

}
