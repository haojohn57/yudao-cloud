package cn.iocoder.yudao.module.system.service.iot.pointattribute;

import cn.iocoder.yudao.framework.common.exception.NotFoundException;
import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointattribute.vo.PointAttributeCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointattribute.vo.PointAttributeExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointattribute.vo.PointAttributePageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointattribute.vo.PointAttributeUpdateReqVO;
import cn.iocoder.yudao.module.system.convert.iot.point.PointAttributeConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.point.PointAttributeDO;
import cn.iocoder.yudao.module.system.dal.mysql.iot.point.PointAttributeMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.POINT_ATTRIBUTE_NOT_EXISTS;

/**
 * 位号配置信息 Service 实现类
 *
 * @author 益莲科技
 */
@Service
@Validated
public class PointAttributeServiceImpl implements PointAttributeService {

    @Resource
    private PointAttributeMapper pointAttributeMapper;

    @Override
    public Long createPointAttribute(PointAttributeCreateReqVO createReqVO) {
        // 插入
        PointAttributeDO pointAttribute = PointAttributeConvert.INSTANCE.convert(createReqVO);
        pointAttributeMapper.insert(pointAttribute);
        // 返回
        return pointAttribute.getId();
    }

    @Override
    public void updatePointAttribute(PointAttributeUpdateReqVO updateReqVO) {
        // 校验存在
        this.validatePointAttributeExists(updateReqVO.getId());
        // 更新
        PointAttributeDO updateObj = PointAttributeConvert.INSTANCE.convert(updateReqVO);
        pointAttributeMapper.updateById(updateObj);
    }

    @Override
    public void deletePointAttribute(Long id) {
        // 校验存在
        this.validatePointAttributeExists(id);
        // 删除
        pointAttributeMapper.deleteById(id);
    }

    private void validatePointAttributeExists(Long id) {
        if (pointAttributeMapper.selectById(id) == null) {
            throw new ServiceException(POINT_ATTRIBUTE_NOT_EXISTS);
        }
    }

    @Override
    public PointAttributeDO getPointAttribute(Long id) {
        return pointAttributeMapper.selectById(id);
    }

    @Override
    public List<PointAttributeDO> getPointAttributeList(Collection<Long> ids) {
        return pointAttributeMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<PointAttributeDO> getPointAttributePage(PointAttributePageReqVO pageReqVO) {
        return pointAttributeMapper.selectPage(pageReqVO);
    }

    @Override
    public List<PointAttributeDO> getPointAttributeList(PointAttributeExportReqVO exportReqVO) {
        return pointAttributeMapper.selectList(exportReqVO);
    }

    @Override
    public PointAttributeDO selectById(String id) {
        PointAttributeDO pointAttribute = pointAttributeMapper.selectById(id);
        if (null == pointAttribute) {
            throw new NotFoundException("The point attribute does not exist");
        }
        return pointAttribute;
    }

    @Override
    public PointAttributeDO selectByNameAndDriverId(String name, Long driverId) {
        LambdaQueryWrapper<PointAttributeDO> queryWrapper = Wrappers.<PointAttributeDO>query().lambda();
        queryWrapper.eq(PointAttributeDO::getName, name);
        queryWrapper.eq(PointAttributeDO::getDriverId, driverId);
        PointAttributeDO pointAttribute = pointAttributeMapper.selectOne(queryWrapper);
        if (null == pointAttribute) {
            throw new NotFoundException("The point attribute does not exist");
        }
        return pointAttribute;
    }

    @Override
    public List<PointAttributeDO> selectByDriverId(Long driverId) {
        PointAttributeExportReqVO pointAttributeReqVO = new PointAttributeExportReqVO();
        pointAttributeReqVO.setDriverId(driverId);
        List<PointAttributeDO> pointAttributes = pointAttributeMapper.selectList(pointAttributeReqVO);
        if (null == pointAttributes || pointAttributes.size() < 1) {
            throw new NotFoundException("The point attributes does not exist");
        }
        return pointAttributes;
    }
}
