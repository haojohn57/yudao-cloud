package cn.iocoder.yudao.framework.common.enums;

/**
 * 数据相关
 *
 * @author 益莲科技
 */
public interface ValueConstants {
    /**
     * 类型相关
     */
    interface Type {
        String HEX = "hex";
        String BYTE = "byte";
        String SHORT = "short";
        String INT = "int";
        String LONG = "long";
        String FLOAT = "float";
        String DOUBLE = "double";
        String BOOLEAN = "boolean";
        String STRING = "string";
    }

    /**
     * 策略工厂相关
     */
    interface StrategyService {
        String POINT_VALUE_SAVE_STRATEGY = "saving" + CacheConstant.Symbol.SEPARATOR;
        String POINT_VALUE_SAVE_STRATEGY_INFLUXDB = "influxdb";
        String POINT_VALUE_SAVE_STRATEGY_OPENTSDB = "opentsdb";
        String POINT_VALUE_SAVE_STRATEGY_ELASTICSEARCH = "elasticsearch";
    }

    /**
     * 存储相关
     */
    interface Storage {
        /**
         * 设备数据存储集合前缀
         */
        String POINT_VALUE_PREFIX = "point_value_";
    }
}
