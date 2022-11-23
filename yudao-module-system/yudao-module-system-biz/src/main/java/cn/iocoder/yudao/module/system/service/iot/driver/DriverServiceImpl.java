package cn.iocoder.yudao.module.system.service.iot.driver;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.framework.common.exception.NotFoundException;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.iot.driver.vo.DriverCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driver.vo.DriverExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driver.vo.DriverPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driver.vo.DriverUpdateReqVO;
import cn.iocoder.yudao.module.system.convert.iot.driver.DriverConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.device.DeviceDO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.driver.DriverDO;
import cn.iocoder.yudao.module.system.dal.mysql.iot.device.DeviceMapper;
import cn.iocoder.yudao.module.system.dal.mysql.iot.driver.DriverMapper;
import cn.iocoder.yudao.module.system.service.iot.profilebind.ProfileBindService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.DRIVER_NOT_EXISTS;

/**
 * 协议驱动 Service 实现类
 *
 * @author 益莲科技
 */
@Service
@Validated
public class DriverServiceImpl implements DriverService {

    @Resource
    private DriverMapper driverMapper;
    @Resource
    private DeviceMapper deviceMapper;
    @Resource
    private ProfileBindService profileBindService;
    @Override
    public Long createDriver(DriverCreateReqVO createReqVO) {
        // 插入
        DriverDO driver = DriverConvert.INSTANCE.convert(createReqVO);
        driverMapper.insert(driver);
        // 返回
        return driver.getId();
    }

    @Override
    public void updateDriver(DriverUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateDriverExists(updateReqVO.getId());
        // 更新
        DriverDO updateObj = DriverConvert.INSTANCE.convert(updateReqVO);
        driverMapper.updateById(updateObj);
    }

    @Override
    public void deleteDriver(Long id) {
        // 校验存在
        this.validateDriverExists(id);
        // 删除
        driverMapper.deleteById(id);
    }

    private void validateDriverExists(Long id) {
        if (driverMapper.selectById(id) == null) {
            throw exception(DRIVER_NOT_EXISTS);
        }
    }

    @Override
    public DriverDO getDriver(Long id) {
        return driverMapper.selectById(id);
    }

    @Override
    public List<DriverDO> getDriverList(Collection<Long> ids) {
        return driverMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DriverDO> getDriverPage(DriverPageReqVO pageReqVO) {
        return driverMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DriverDO> getDriverList(DriverExportReqVO exportReqVO) {
        return driverMapper.selectList(exportReqVO);
    }

    @Override
    public DriverDO selectById(Long driverId) {
        DriverDO driver = driverMapper.selectById(driverId);
        if (null == driver) {
            throw new NotFoundException("The driver does not exist");
        }
        return driver;
    }

    @Override
    public DriverDO selectByDeviceId(Long deviceId) {
        DeviceDO device = deviceMapper.selectById(deviceId);
        if (ObjectUtil.isNotNull(device)) {
            return selectById(device.getDriverId());
        }
        throw new NotFoundException("The device does not exist");
    }

    @Override
    public DriverDO selectByServiceName(String serviceName) {

        DriverDO driver = driverMapper.selectOne(
                new LambdaQueryWrapperX<DriverDO>()
                        .eq(DriverDO::getServiceName, serviceName));
        if (null == driver) {
            throw new NotFoundException("The driver does not exist");
        }
        return driver;
    }

    @Override
    public DriverDO selectByHostPort(String type, String host, Integer port, Long tenantId) {
        DriverDO driver = driverMapper.selectOne(
                new LambdaQueryWrapperX<DriverDO>()
                        .eq(DriverDO::getType, type)
                        .eq(DriverDO::getHost, host)
                        .eq(DriverDO::getPort, port)
                        .eq(DriverDO::getTenantId, tenantId)
        );
        if (null == driver) {
            throw new NotFoundException("The driver does not exist");
        }
        return driver;
    }

    @Override
    public List<DriverDO> selectByIds(Set<Long> ids) {
        List<DriverDO> drivers = driverMapper.selectBatchIds(ids);
        if (CollectionUtil.isEmpty(drivers)) {
            throw new NotFoundException("The driver does not exist");
        }
        return drivers;
    }

    @Override
    public List<DriverDO> selectByProfileId(Long profileId) {
        Set<Long> deviceIds = profileBindService.selectDeviceIdByProfileId(profileId);
        List<DeviceDO> devices = deviceMapper.selectBatchIds(deviceIds);
        if (CollectionUtil.isEmpty(devices)) {
            throw new NotFoundException("The devices does not exist");
        }
        Set<Long> driverIds = devices.stream().map(DeviceDO::getDriverId).collect(Collectors.toSet());
        return selectByIds(driverIds);
    }

}
