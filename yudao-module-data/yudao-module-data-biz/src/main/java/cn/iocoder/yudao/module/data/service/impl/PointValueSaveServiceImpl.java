package cn.iocoder.yudao.module.data.service.impl;

import cn.iocoder.yudao.framework.common.enums.ValueConstants;
import cn.iocoder.yudao.framework.common.iot.bean.point.PointValue;
import cn.iocoder.yudao.module.data.service.PointValueHandleService;
import cn.iocoder.yudao.module.data.strategy.factory.SaveStrategyFactory;
import cn.iocoder.yudao.module.data.strategy.service.SaveStrategyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 益莲科技
 */
@Slf4j
@Service
public class PointValueSaveServiceImpl implements PointValueHandleService {

    @Value("${data.point.sava.opentsdb.enable}")
    private Boolean enableOpentsdb;
    @Value("${data.point.sava.elasticsearch.enable}")
    private Boolean enableElasticsearch;

    @Override
    public void postHandle(PointValue pointValue) {
        if (enableOpentsdb) {
            SaveStrategyService saveStrategyService = SaveStrategyFactory.get(ValueConstants.StrategyService.POINT_VALUE_SAVE_STRATEGY_OPENTSDB);
            saveStrategyService.savePointValue(pointValue);
        }

        if (enableElasticsearch) {
            SaveStrategyService saveStrategyService = SaveStrategyFactory.get(ValueConstants.StrategyService.POINT_VALUE_SAVE_STRATEGY_ELASTICSEARCH);
            saveStrategyService.savePointValue(pointValue);
        }
    }

    @Override
    public void postHandle(List<PointValue> pointValues) {
        if (enableOpentsdb) {
            SaveStrategyService saveStrategyService = SaveStrategyFactory.get(ValueConstants.StrategyService.POINT_VALUE_SAVE_STRATEGY_OPENTSDB);
            saveStrategyService.savePointValues(pointValues);
        }

        if (enableElasticsearch) {
            SaveStrategyService saveStrategyService = SaveStrategyFactory.get(ValueConstants.StrategyService.POINT_VALUE_SAVE_STRATEGY_ELASTICSEARCH);
            saveStrategyService.savePointValues(pointValues);
        }
    }

}
