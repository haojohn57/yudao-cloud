package cn.iocoder.yudao.module.system.api.iot.point;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.api.iot.point.dto.PointRespDTO;
import cn.iocoder.yudao.module.system.convert.iot.point.PointConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.point.PointDO;
import cn.iocoder.yudao.module.system.service.iot.point.PointService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.module.system.enums.ApiConstants.VERSION;

/**
 * 位号 FeignClient
 *
 * @author 益莲科技
 */
@RestController // 提供 RESTful API 接口，给 Feign 调用
@DubboService(version = VERSION) // 提供 Dubbo RPC 接口，给 Dubbo Consumer 调用
@Validated
public class PointApiImpl implements PointApi{

    @Resource
    private PointService pointService;


    /**
     * 根据 ID 查询 Point
     *
     * @param id Point Id
     * @return Point
     */
    @Override
    public CommonResult<PointRespDTO> selectById(Long id) {
        PointDO select = pointService.selectById(id);
        return success(PointConvert.INSTANCE.convert4(select));
    }

    /**
     * 根据 设备 ID 查询 Point
     *
     * @param deviceId Device Id
     * @return Point Array
     */
    @Override
    public CommonResult<List<PointRespDTO>> selectByDeviceId(Long deviceId) {
        List<PointDO> select = pointService.selectByDeviceId(deviceId);
        return success(PointConvert.INSTANCE.convertList03(select));
    }

    /**
     * 根据 模板 ID 查询 Point
     *
     * @param profileId Profile Id
     * @return Point Array
     */
    @Override
    public CommonResult<List<PointRespDTO>> selectByProfileId(Long profileId) {
        List<PointDO> select = pointService.selectByProfileId(profileId);
        return success(PointConvert.INSTANCE.convertList03(select));
    }

    /**
     * 查询 位号单位
     *
     * @param pointIds Point Id Set
     * @return Map<Long, String>
     */
    @Override
    public CommonResult<Map<Long, String>> unit(Set<Long> pointIds) {
        Map<Long, String> units = pointService.unit(pointIds);
        return success(units);

    }
}
