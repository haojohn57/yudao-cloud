package cn.iocoder.yudao.module.data.strategy.factory;

import cn.iocoder.yudao.framework.common.enums.ValueConstants;
import cn.iocoder.yudao.module.data.strategy.service.SaveStrategyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Point Value 存储策略工厂
 *
 * @author 益莲科技
 */
public class SaveStrategyFactory {
    private static final Map<String, SaveStrategyService> savingStrategyServiceMap = new ConcurrentHashMap<>();

    public static List<SaveStrategyService> getAll() {
        return new ArrayList<>(savingStrategyServiceMap.values());
    }

    public static SaveStrategyService get(String name) {
        return savingStrategyServiceMap.get(ValueConstants.StrategyService.POINT_VALUE_SAVE_STRATEGY + name);
    }

    public static void put(String name, SaveStrategyService printParamStrategyService) {
        savingStrategyServiceMap.put(ValueConstants.StrategyService.POINT_VALUE_SAVE_STRATEGY + name, printParamStrategyService);
    }
}
