package cn.iocoder.yudao.module.system.service.iot.driverattribute;

import cn.iocoder.yudao.framework.common.exception.NotFoundException;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverattribute.vo.DriverAttributeCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverattribute.vo.DriverAttributeExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverattribute.vo.DriverAttributePageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverattribute.vo.DriverAttributeUpdateReqVO;
import cn.iocoder.yudao.module.system.convert.iot.driver.DriverAttributeConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.driver.DriverAttributeDO;
import cn.iocoder.yudao.module.system.dal.mysql.iot.driver.DriverAttributeMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.DRIVER_ATTRIBUTE_NOT_EXISTS;

/**
 * 连接配置信息 Service 实现类
 *
 * @author 益莲科技
 */
@Service
@Validated
public class DriverAttributeServiceImpl implements DriverAttributeService {

    @Resource
    private DriverAttributeMapper driverAttributeMapper;

    @Override
    public Long createDriverAttribute(DriverAttributeCreateReqVO createReqVO) {
        // 插入
        DriverAttributeDO driverAttribute = DriverAttributeConvert.INSTANCE.convert(createReqVO);
        driverAttributeMapper.insert(driverAttribute);
        // 返回
        return driverAttribute.getId();
    }

    @Override
    public void updateDriverAttribute(DriverAttributeUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateDriverAttributeExists(updateReqVO.getId());
        // 更新
        DriverAttributeDO updateObj = DriverAttributeConvert.INSTANCE.convert(updateReqVO);
        driverAttributeMapper.updateById(updateObj);
    }

    @Override
    public void deleteDriverAttribute(Long id) {
        // 校验存在
        this.validateDriverAttributeExists(id);
        // 删除
        driverAttributeMapper.deleteById(id);
    }

    private void validateDriverAttributeExists(Long id) {
        if (driverAttributeMapper.selectById(id) == null) {
            throw exception(DRIVER_ATTRIBUTE_NOT_EXISTS);
        }
    }

    @Override
    public DriverAttributeDO getDriverAttribute(Long id) {
        return driverAttributeMapper.selectById(id);
    }

    @Override
    public List<DriverAttributeDO> getDriverAttributeList(Collection<Long> ids) {
        return driverAttributeMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DriverAttributeDO> getDriverAttributePage(DriverAttributePageReqVO pageReqVO) {
        return driverAttributeMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DriverAttributeDO> getDriverAttributeList(DriverAttributeExportReqVO exportReqVO) {
        return driverAttributeMapper.selectList(exportReqVO);
    }
    @Override
    public DriverAttributeDO selectById(Long id) {
        DriverAttributeDO driverAttribute = driverAttributeMapper.selectById(id);
        if (null == driverAttribute) {
            throw new NotFoundException("The driver attribute does not exist");
        }
        return driverAttribute;
    }

    @Override
    public DriverAttributeDO selectByNameAndDriverId(String name, Long driverId) {
        LambdaQueryWrapper<DriverAttributeDO> queryWrapper = Wrappers.<DriverAttributeDO>query().lambda();
        queryWrapper.eq(DriverAttributeDO::getName, name);
        queryWrapper.eq(DriverAttributeDO::getDriverId, driverId);
        DriverAttributeDO driverAttribute = driverAttributeMapper.selectOne(queryWrapper);
        if (null == driverAttribute) {
            throw new NotFoundException("The driver attribute does not exist");
        }
        return driverAttribute;
    }

    @Override
    public List<DriverAttributeDO> selectByDriverId(Long driverId) {
        DriverAttributeExportReqVO reqVO = new DriverAttributeExportReqVO();
        reqVO.setDriverId(driverId);
        List<DriverAttributeDO> driverAttributes = driverAttributeMapper.selectList(reqVO);
        if (null == driverAttributes || driverAttributes.size() < 1) {
            throw new NotFoundException("The driver attributes does not exist");
        }
        return driverAttributes;
    }
}
