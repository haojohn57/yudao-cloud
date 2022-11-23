package cn.iocoder.yudao.module.system.service.iot.driverinfo;

import cn.iocoder.yudao.framework.common.exception.NotFoundException;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverinfo.vo.DriverInfoCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverinfo.vo.DriverInfoExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverinfo.vo.DriverInfoPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverinfo.vo.DriverInfoUpdateReqVO;
import cn.iocoder.yudao.module.system.convert.iot.driver.DriverInfoConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.driver.DriverInfoDO;
import cn.iocoder.yudao.module.system.dal.mysql.iot.driver.DriverInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.DRIVER_INFO_NOT_EXISTS;

/**
 * 协议配置信息 Service 实现类
 *
 * @author 益莲科技
 */
@Service
@Validated
public class DriverInfoServiceImpl implements DriverInfoService {

    @Resource
    private DriverInfoMapper driverInfoMapper;

    @Override
    public Long createDriverInfo(DriverInfoCreateReqVO createReqVO) {
        // 插入
        DriverInfoDO driverInfo = DriverInfoConvert.INSTANCE.convert(createReqVO);
        driverInfoMapper.insert(driverInfo);
        // 返回
        return driverInfo.getId();
    }

    @Override
    public void updateDriverInfo(DriverInfoUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateDriverInfoExists(updateReqVO.getId());
        // 更新
        DriverInfoDO updateObj = DriverInfoConvert.INSTANCE.convert(updateReqVO);
        driverInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteDriverInfo(Long id) {
        // 校验存在
        this.validateDriverInfoExists(id);
        // 删除
        driverInfoMapper.deleteById(id);
    }

    private void validateDriverInfoExists(Long id) {
        if (driverInfoMapper.selectById(id) == null) {
            throw exception(DRIVER_INFO_NOT_EXISTS);
        }
    }

    @Override
    public DriverInfoDO getDriverInfo(Long id) {
        return driverInfoMapper.selectById(id);
    }

    @Override
    public List<DriverInfoDO> getDriverInfoList(Collection<Long> ids) {
        return driverInfoMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DriverInfoDO> getDriverInfoPage(DriverInfoPageReqVO pageReqVO) {
        return driverInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DriverInfoDO> getDriverInfoList(DriverInfoExportReqVO exportReqVO) {
        return driverInfoMapper.selectList(exportReqVO);
    }

    @Override
    public DriverInfoDO selectById(Long id) {
        DriverInfoDO driverInfo = driverInfoMapper.selectById(id);
        if (null == driverInfo) {
            throw new NotFoundException("The driver info does not exist");
        }
        return driverInfo;
    }

    @Override
    public DriverInfoDO selectByAttributeIdAndDeviceId(Long driverAttributeId, Long deviceId) {
        LambdaQueryWrapper<DriverInfoDO> queryWrapper = Wrappers.<DriverInfoDO>query().lambda();
        queryWrapper.eq(DriverInfoDO::getDriverAttributeId, driverAttributeId);
        queryWrapper.eq(DriverInfoDO::getDeviceId, deviceId);
        DriverInfoDO driverInfo = driverInfoMapper.selectOne(queryWrapper);
        if (null == driverInfo) {
            throw new NotFoundException("The driver info does not exist");
        }
        return driverInfo;
    }

    @Override
    public List<DriverInfoDO> selectByAttributeId(Long driverAttributeId) {
        LambdaQueryWrapper<DriverInfoDO> queryWrapper = Wrappers.<DriverInfoDO>query().lambda();
        queryWrapper.eq(DriverInfoDO::getDriverAttributeId, driverAttributeId);
        List<DriverInfoDO> driverInfos = driverInfoMapper.selectList(queryWrapper);
        if (null == driverInfos || driverInfos.size() < 1) {
            throw new NotFoundException("The driver infos does not exist");
        }
        return driverInfos;
    }

    @Override
    public List<DriverInfoDO> selectByDeviceId(Long deviceId) {
        LambdaQueryWrapper<DriverInfoDO> queryWrapper = Wrappers.<DriverInfoDO>query().lambda();
        queryWrapper.eq(DriverInfoDO::getDeviceId, deviceId);
        List<DriverInfoDO> driverInfos = driverInfoMapper.selectList(queryWrapper);
        if (null == driverInfos || driverInfos.size() < 1) {
            throw new NotFoundException("The driver infos does not exist");
        }
        return driverInfos;
    }
}
