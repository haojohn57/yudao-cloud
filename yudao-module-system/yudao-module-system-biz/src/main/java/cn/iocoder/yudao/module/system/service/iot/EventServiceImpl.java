package cn.iocoder.yudao.module.system.service.iot;

import cn.iocoder.yudao.framework.common.iot.bean.Pages;
import cn.iocoder.yudao.framework.common.iot.dto.DeviceEventDto;
import cn.iocoder.yudao.framework.common.iot.dto.DriverEventDto;
import cn.iocoder.yudao.framework.common.iot.model.DeviceEvent;
import cn.iocoder.yudao.framework.common.iot.model.DriverEvent;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 益莲科技
 */
@Slf4j
@Service
public class EventServiceImpl implements EventService {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void addDriverEvent(DriverEvent driverEvent) {
        if (null != driverEvent) {
            mongoTemplate.insert(driverEvent);
        }
    }

    @Override
    public void addDriverEvents(List<DriverEvent> driverEvents) {
        if (null != driverEvents) {
            if (driverEvents.size() > 0) {
                mongoTemplate.insert(driverEvents, DriverEvent.class);
            }
        }
    }

    @Override
    public void addDeviceEvent(DeviceEvent deviceEvent) {
        if (null != deviceEvent) {
            mongoTemplate.insert(deviceEvent);
        }
    }

    @Override
    public void addDeviceEvents(List<DeviceEvent> deviceEvents) {
        if (null != deviceEvents) {
            if (deviceEvents.size() > 0) {
                mongoTemplate.insert(deviceEvents, DeviceEvent.class);
            }
        }
    }

    @Override
    public Page<DriverEvent> driverEvent(DriverEventDto driverEventDto) {
        return null;
    }

    @Override
    public Page<DeviceEvent> deviceEvent(DeviceEventDto deviceEventDto) {
        Criteria criteria = new Criteria();
        if (null == deviceEventDto) {
            deviceEventDto = new DeviceEventDto();
        }
        if (null != deviceEventDto.getDeviceId()) {
            criteria.and("deviceId").is(deviceEventDto.getDeviceId());
        }
        if (null != deviceEventDto.getPointId()) {
            criteria.and("pointId").is(deviceEventDto.getPointId());
        }

        Pages pages = null == deviceEventDto.getPage() ? new Pages() : deviceEventDto.getPage();
        if (pages.getStartTime() > 0 && pages.getEndTime() > 0 && pages.getStartTime() <= pages.getEndTime()) {
            criteria.and("originTime").gte(pages.getStartTime()).lte(pages.getEndTime());
        }

        Query query = new Query(criteria);
        long count = mongoTemplate.count(query, DeviceEvent.class);

        query.with(Sort.by(Sort.Direction.DESC, "originTime"));
        long size = pages.getSize();
        long page = pages.getCurrent();
        query.limit((int) size).skip(size * (page - 1));

        List<DeviceEvent> deviceEvents = mongoTemplate.find(query, DeviceEvent.class);

        return (new Page<DeviceEvent>()).setCurrent(pages.getCurrent()).setSize(pages.getSize()).setTotal(count).setRecords(deviceEvents);
    }

}
