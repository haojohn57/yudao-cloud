package cn.iocoder.yudao.module.system.service.iot.pointinfo;

import cn.iocoder.yudao.framework.common.exception.NotFoundException;
import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointinfo.vo.PointInfoCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointinfo.vo.PointInfoExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointinfo.vo.PointInfoPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointinfo.vo.PointInfoUpdateReqVO;
import cn.iocoder.yudao.module.system.convert.iot.point.PointInfoConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.point.PointDO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.point.PointInfoDO;
import cn.iocoder.yudao.module.system.dal.mysql.iot.point.PointInfoMapper;
import cn.iocoder.yudao.module.system.service.iot.point.PointService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.POINT_INFO_NOT_EXISTS;

/**
 * 位号配置信息 Service 实现类
 *
 * @author 益莲科技
 */
@Service
@Validated
public class PointInfoServiceImpl implements PointInfoService {

    @Resource
    private PointService pointService;

    @Resource
    private PointInfoMapper pointInfoMapper;


    @Override
    public Long createPointInfo(PointInfoCreateReqVO createReqVO) {
        // 插入
        PointInfoDO pointInfo = PointInfoConvert.INSTANCE.convert(createReqVO);
        pointInfoMapper.insert(pointInfo);
        // 返回
        return pointInfo.getId();
    }

    @Override
    public void updatePointInfo(PointInfoUpdateReqVO updateReqVO) {
        // 校验存在
        this.validatePointInfoExists(updateReqVO.getId());
        // 更新
        PointInfoDO updateObj = PointInfoConvert.INSTANCE.convert(updateReqVO);
        pointInfoMapper.updateById(updateObj);
    }

    @Override
    public void deletePointInfo(Long id) {
        // 校验存在
        this.validatePointInfoExists(id);
        // 删除
        pointInfoMapper.deleteById(id);
    }

    private void validatePointInfoExists(Long id) {
        if (pointInfoMapper.selectById(id) == null) {
            throw new ServiceException(POINT_INFO_NOT_EXISTS);
        }
    }

    @Override
    public PointInfoDO getPointInfo(Long id) {
        return pointInfoMapper.selectById(id);
    }

    @Override
    public List<PointInfoDO> getPointInfoList(Collection<Long> ids) {
        return pointInfoMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<PointInfoDO> getPointInfoPage(PointInfoPageReqVO pageReqVO) {
        return pointInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public List<PointInfoDO> getPointInfoList(PointInfoExportReqVO exportReqVO) {
        return pointInfoMapper.selectList(exportReqVO);
    }

    @Override
    public PointInfoDO selectById(Long id) {
        PointInfoDO pointInfo = pointInfoMapper.selectById(id);
        if (null == pointInfo) {
            throw new NotFoundException("The point info does not exist");
        }
        return pointInfo;
    }

    @Override
    public PointInfoDO selectByAttributeIdAndDeviceIdAndPointId(Long pointAttributeId, Long deviceId, Long pointId) {
        LambdaQueryWrapper<PointInfoDO> queryWrapper = Wrappers.<PointInfoDO>query().lambda();
        queryWrapper.eq(PointInfoDO::getPointAttributeId, pointAttributeId);
        queryWrapper.eq(PointInfoDO::getDeviceId, deviceId);
        queryWrapper.eq(PointInfoDO::getPointId, pointId);
        PointInfoDO pointInfo = pointInfoMapper.selectOne(queryWrapper);
        if (null == pointInfo) {
            throw new NotFoundException("The point info does not exist");
        }
        return pointInfo;
    }

    @Override
    public List<PointInfoDO> selectByAttributeId(Long pointAttributeId) {
        LambdaQueryWrapper<PointInfoDO> queryWrapper = Wrappers.<PointInfoDO>query().lambda();
        queryWrapper.eq(PointInfoDO::getPointAttributeId, pointAttributeId);
        List<PointInfoDO> pointInfos = pointInfoMapper.selectList(queryWrapper);
        if (null == pointInfos || pointInfos.size() < 1) {
            throw new NotFoundException("The point infos does not exist");
        }
        return pointInfos;
    }

    @Override
    public List<PointInfoDO> selectByDeviceId(Long deviceId) {
        LambdaQueryWrapper<PointInfoDO> queryWrapper = Wrappers.<PointInfoDO>query().lambda();
        List<PointDO> points = pointService.selectByDeviceId(deviceId);
        Set<Long> pointIds = points.stream().map(PointDO::getId).collect(Collectors.toSet());
        queryWrapper.eq(PointInfoDO::getDeviceId, deviceId);
        queryWrapper.in(PointInfoDO::getPointId, pointIds);
        List<PointInfoDO> pointInfos = pointInfoMapper.selectList(queryWrapper);
        if (null == pointInfos) {
            throw new NotFoundException("The point infos does not exist");
        }
        return pointInfos;
    }

    @Override
    public List<PointInfoDO> selectByDeviceIdAndPointId(Long deviceId, Long pointId) {
        LambdaQueryWrapper<PointInfoDO> queryWrapper = Wrappers.<PointInfoDO>query().lambda();
        queryWrapper.eq(PointInfoDO::getDeviceId, deviceId);
        queryWrapper.eq(PointInfoDO::getPointId, pointId);
        List<PointInfoDO> pointInfos = pointInfoMapper.selectList(queryWrapper);
        if (null == pointInfos) {
            throw new NotFoundException("The point infos does not exist");
        }
        return pointInfos;
    }


}
