package cn.iocoder.yudao.module.system.service.iot.point;

import cn.iocoder.yudao.framework.common.exception.NotFoundException;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.iot.point.vo.PointCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.point.vo.PointExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.point.vo.PointPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.point.vo.PointUpdateReqVO;
import cn.iocoder.yudao.module.system.convert.iot.point.PointConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.point.PointDO;
import cn.iocoder.yudao.module.system.dal.mysql.iot.point.PointMapper;
import cn.iocoder.yudao.module.system.service.iot.profilebind.ProfileBindService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.POINT_NOT_EXISTS;

/**
 * 设备位号 Service 实现类
 *
 * @author 益莲科技
 */
@Service
@Validated
public class PointServiceImpl implements PointService {

    @Resource
    private PointMapper pointMapper;
    @Resource
    private ProfileBindService profileBindService;

    @Override
    public Long createPoint(PointCreateReqVO createReqVO) {
        // 插入
        PointDO point = PointConvert.INSTANCE.convert(createReqVO);
        pointMapper.insert(point);
        // 返回
        return point.getId();
    }

    @Override
    public void updatePoint(PointUpdateReqVO updateReqVO) {
        // 校验存在
        this.validatePointExists(updateReqVO.getId());
        // 更新
        PointDO updateObj = PointConvert.INSTANCE.convert(updateReqVO);
        pointMapper.updateById(updateObj);
    }

    @Override
    public void deletePoint(Long id) {
        // 校验存在
        this.validatePointExists(id);
        // 删除
        pointMapper.deleteById(id);
    }

    private void validatePointExists(Long id) {
        if (pointMapper.selectById(id) == null) {
            throw exception(POINT_NOT_EXISTS);
        }
    }

    @Override
    public PointDO getPoint(Long id) {
        return pointMapper.selectById(id);
    }

    @Override
    public List<PointDO> getPointList(Collection<Long> ids) {
        return pointMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<PointDO> getPointPage(PointPageReqVO pageReqVO) {
        return pointMapper.selectPage(pageReqVO);
    }

    @Override
    public List<PointDO> getPointList(PointExportReqVO exportReqVO) {
        return pointMapper.selectList(exportReqVO);
    }

    @Override
    public PointDO selectById(Long id) {
        PointDO point = pointMapper.selectById(id);
        if (null == point) {
            throw new NotFoundException("The point does not exist");
        }
        return point;
    }

    @Override
    public PointDO selectByNameAndProfileId(String name, Long profileId) {
        LambdaQueryWrapper<PointDO> queryWrapper = Wrappers.<PointDO>query().lambda();
        queryWrapper.eq(PointDO::getName, name);
        queryWrapper.eq(PointDO::getProfileId, profileId);
        PointDO point = pointMapper.selectOne(queryWrapper);
        if (null == point) {
            throw new NotFoundException("The point does not exist");
        }
        return point;
    }

    @Override
    public List<PointDO> selectByDeviceId(Long deviceId) {
        Set<Long> profileIds = profileBindService.selectProfileIdByDeviceId(deviceId);
        return selectByProfileIds(profileIds);
    }

    @Override
    public List<PointDO> selectByProfileId(Long profileId) {
        PointExportReqVO pointReqVO = new PointExportReqVO();
        pointReqVO.setProfileId(profileId);
        List<PointDO> points = pointMapper.selectList(pointReqVO);
        if (null == points || points.size() < 1) {
            throw new NotFoundException("The points does not exist");
        }
        return points;
    }

    @Override
    public List<PointDO> selectByProfileIds(Set<Long> profileIds) {
        List<PointDO> points = new ArrayList<>(16);
        profileIds.forEach(profileId -> {
            PointExportReqVO pointReqVO = new PointExportReqVO();
            pointReqVO.setProfileId(profileId);
            List<PointDO> pointList = pointMapper.selectList(pointReqVO);
            if (null != pointList) {
                points.addAll(pointList);
            }
        });
        if (points.size() < 1) {
            throw new NotFoundException("The points does not exist");
        }
        return points;
    }


    @Override
    public Map<Long, String> unit(Set<Long> pointIds) {
        List<PointDO> points = pointMapper.selectBatchIds(pointIds);
        return points.stream().collect(Collectors.toMap(PointDO::getId, PointDO::getUnit));
    }

}
