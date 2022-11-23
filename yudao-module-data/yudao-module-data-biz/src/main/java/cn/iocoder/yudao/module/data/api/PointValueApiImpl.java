package cn.iocoder.yudao.module.data.api;

import cn.iocoder.yudao.framework.common.enums.ValueConstants;
import cn.iocoder.yudao.framework.common.iot.bean.Pages;
import cn.iocoder.yudao.framework.common.iot.bean.point.PointValue;
import cn.iocoder.yudao.framework.common.iot.dto.PointValueDto;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.data.api.point.PointValueApi;
import cn.iocoder.yudao.module.data.service.PointValueService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.module.system.enums.ApiConstants.VERSION;

/**
 * @author 益莲科技
 */
@RestController // 提供 RESTful API 接口，给 Feign 调用
@DubboService(version = VERSION) // 提供 Dubbo RPC 接口，给 Dubbo Consumer 调用
@Validated
public class PointValueApiImpl implements PointValueApi {

    @Resource
    private PointValueService pointValueService;

    @Override
    public CommonResult<List<PointValue>> latest(Long deviceId, Boolean history) {
        try {
            List<PointValue> pointValues = pointValueService.realtime(deviceId);
            if (null == pointValues) {
                pointValues = pointValueService.latest(deviceId);
            }
            if (null != pointValues) {
                // 返回最近100个非字符类型的历史值
                if (history) {
                    pointValues.forEach(pointValue -> {
                        if (!pointValue.getType().equals(ValueConstants.Type.STRING)) {
                            PointValueDto pointValueDto = (new PointValueDto()).setDeviceId(deviceId).setPointId(pointValue.getPointId()).setPage((new Pages()).setSize(100));
                            Page<PointValue> page = pointValueService.list(pointValueDto);
                            if (null != page) {
                                pointValue.setChildren(page.getRecords().stream()
                                        .map(pointValueChild -> pointValueChild.setId(null).setDeviceId(null).setPointId(null)).collect(Collectors.toList()));
                            }
                        }
                    });
                }
                return CommonResult.success(pointValues);
            }
        } catch (Exception e) {
            return CommonResult.error(1000,e.getMessage()); //Todo error code
        }
        return CommonResult.error(1000,"hh"); //
    }

    @Override
    public CommonResult<PointValue> latest(Long deviceId, Long pointId, Boolean history) {
        try {
            PointValue pointValue = pointValueService.realtime(deviceId, pointId);
            if (null == pointValue) {
                pointValue = pointValueService.latest(deviceId, pointId);
            }
            if (null != pointValue) {
                // 返回最近100个非字符类型的历史值
                if (history) {
                    PointValueDto pointValueDto = (new PointValueDto()).setDeviceId(deviceId).setPointId(pointId).setPage((new Pages()).setSize(100));
                    Page<PointValue> page = pointValueService.list(pointValueDto);
                    if (null != page) {
                        pointValue.setChildren(page.getRecords().stream()
                                .map(pointValueChild -> pointValueChild.setId(null).setDeviceId(null).setPointId(null)).collect(Collectors.toList()));
                    }
                }
                return CommonResult.success(pointValue);
            }
        } catch (Exception e) {
            return CommonResult.error(1000,e.getMessage());
        }
        return CommonResult.error(1000,"123");
    }

    @Override
    public CommonResult<Page<PointValue>> list(PointValueDto pointValueDto) {
        try {
            Page<PointValue> page = pointValueService.list(pointValueDto);
            if (null != page) {
                return CommonResult.success(page);
            }
        } catch (Exception e) {
            return CommonResult.error(1000,e.getMessage());
        }
        return CommonResult.error(1000,"123");
    }

}