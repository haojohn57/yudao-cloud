package cn.iocoder.yudao.module.system.service.iot.driver;

import cn.iocoder.yudao.framework.common.exception.NotFoundException;
import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.iot.bean.driver.DriverRegister;
import cn.iocoder.yudao.framework.common.iot.model.Driver;
import cn.iocoder.yudao.framework.common.iot.model.DriverAttribute;
import cn.iocoder.yudao.framework.common.iot.model.PointAttribute;
import cn.iocoder.yudao.framework.tenant.core.context.TenantContextHolder;
import cn.iocoder.yudao.module.system.controller.admin.iot.driver.vo.DriverUpdateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverattribute.vo.DriverAttributeCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverattribute.vo.DriverAttributeUpdateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointattribute.vo.PointAttributeCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointattribute.vo.PointAttributeUpdateReqVO;
import cn.iocoder.yudao.module.system.convert.iot.driver.DriverAttributeConvert;
import cn.iocoder.yudao.module.system.convert.iot.driver.DriverConvert;
import cn.iocoder.yudao.module.system.convert.iot.point.PointAttributeConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.driver.DriverAttributeDO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.driver.DriverDO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.point.PointAttributeDO;
import cn.iocoder.yudao.module.system.dal.dataobject.tenant.TenantDO;
import cn.iocoder.yudao.module.system.service.iot.driverattribute.DriverAttributeService;
import cn.iocoder.yudao.module.system.service.iot.driverinfo.DriverInfoService;
import cn.iocoder.yudao.module.system.service.iot.pointattribute.PointAttributeService;
import cn.iocoder.yudao.module.system.service.iot.pointinfo.PointInfoService;
import cn.iocoder.yudao.module.system.service.tenant.TenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * DriverService Impl
 *
 * @author 益莲科技
 */
@Slf4j
@Service
public class DriverSdkServiceImpl implements DriverSdkService {

    @Resource
    private TenantService tenantService;

    @Resource
    private DriverService driverService;
    @Resource
    private DriverAttributeService driverAttributeService;
    @Resource
    private DriverInfoService driverInfoService;
    @Resource
    private PointAttributeService pointAttributeService;
    @Resource
    private PointInfoService pointInfoService;

    @Override
    public void driverRegister(DriverRegister driverRegister) {
        // check tenant
        TenantDO tenantR = tenantService.getTenantByName(driverRegister.getTenant());
        try {
            tenantService.validTenant(tenantR.getId());
        } catch (Throwable ex) {
           log.error(String.valueOf(ex)); // Todo:hao
       }

        // register driver
        Driver driver = driverRegister.getDriver().setTenantId(tenantR.getId());
        TenantContextHolder.setTenantId(tenantR.getId());   // Tenant need set contextholder
        log.info("Register driver {}", driver);
        try {
            DriverDO byServiceName = driverService.selectByServiceName(driver.getServiceName());
            log.debug("Driver already registered, updating {} ", driver);
            driver.setId(byServiceName.getId());    // update 时写入driverId
            DriverUpdateReqVO updateReqVO = DriverConvert.INSTANCE.convert4(driver);
            driverService.updateDriver(updateReqVO);
        } catch (NotFoundException notFoundException1) {
            log.debug("Driver does not registered, adding {} ", driver);
            try {
                DriverDO byHostPort = driverService.selectByHostPort(driver.getType(), driver.getHost(), driver.getPort(), driver.getTenantId());
                throw new ServiceException(DRIVER_PORT_DUPLICATE);
            } catch (NotFoundException notFoundException2) {
                Long driverId = driverService.createDriver(DriverConvert.INSTANCE.convert3(driver));
                driver.setId(driverId);   // create 时把相应的id写入driver
            }
        }

        //register driver attribute
        Map<String, DriverAttribute> newDriverAttributeMap = new HashMap<>(8);
        if (null != driverRegister.getDriverAttributes() && driverRegister.getDriverAttributes().size() > 0) {
            driverRegister.getDriverAttributes().forEach(driverAttribute -> newDriverAttributeMap.put(driverAttribute.getName(), driverAttribute));
        }

        Map<String, DriverAttributeDO> oldDriverAttributeMap = new HashMap<>(8);
        try {
            List<DriverAttributeDO> byDriverId = driverAttributeService.selectByDriverId(driver.getId());
            byDriverId.forEach(driverAttribute -> oldDriverAttributeMap.put(driverAttribute.getName(), driverAttribute));
        } catch (NotFoundException ignored) {
        }

        for (String name : newDriverAttributeMap.keySet()) {
            DriverAttribute info = newDriverAttributeMap.get(name).setDriverId(driver.getId());
            if (oldDriverAttributeMap.containsKey(name)) {
                info.setId(oldDriverAttributeMap.get(name).getId());
                log.debug("Driver attribute registered, updating: {}", info);
                DriverAttributeUpdateReqVO updateReqVO = DriverAttributeConvert.INSTANCE.convert1(info);
                driverAttributeService.updateDriverAttribute(updateReqVO);
            } else {
                log.debug("Driver attribute does not registered, adding: {}", info);
                DriverAttributeCreateReqVO createReqVO = DriverAttributeConvert.INSTANCE.convert2(info);
                driverAttributeService.createDriverAttribute(createReqVO);
            }
        }

        for (String name : oldDriverAttributeMap.keySet()) {
            if (!newDriverAttributeMap.containsKey(name)) {
                try {
                    driverInfoService.selectByAttributeId(oldDriverAttributeMap.get(name).getId());
                    throw new ServiceException(DRIVER_ATTRIBUTE_USED_CANNOT_DELETED);
                } catch (NotFoundException notFoundException) {
                    log.debug("Driver attribute is redundant, deleting: {}", oldDriverAttributeMap.get(name));
                    driverAttributeService. deleteDriverAttribute(oldDriverAttributeMap.get(name).getId());
                }
            }
        }

        // register point attribute
        Map<String, PointAttribute> newPointAttributeMap = new HashMap<>(8);
        if (null != driverRegister.getPointAttributes() && driverRegister.getPointAttributes().size() > 0) {
            driverRegister.getPointAttributes().forEach(pointAttribute -> newPointAttributeMap.put(pointAttribute.getName(), pointAttribute));
        }

        Map<String, PointAttributeDO> oldPointAttributeMap = new HashMap<>(8);
        try {
            List<PointAttributeDO> byDriverId = pointAttributeService.selectByDriverId(driver.getId());
            byDriverId.forEach(pointAttribute -> oldPointAttributeMap.put(pointAttribute.getName(), pointAttribute));
        } catch (NotFoundException ignored) {
        }

        for (String name : newPointAttributeMap.keySet()) {
            PointAttribute attribute = newPointAttributeMap.get(name).setDriverId(driver.getId());
            if (oldPointAttributeMap.containsKey(name)) {
                attribute.setId(oldPointAttributeMap.get(name).getId());
                log.debug("Point attribute registered, updating: {}", attribute);
                PointAttributeUpdateReqVO updateReqVO = PointAttributeConvert.INSTANCE.convert1(attribute);
                pointAttributeService.updatePointAttribute(updateReqVO);
            } else {
                log.debug("Point attribute registered, adding: {}", attribute);
                PointAttributeCreateReqVO createReqVO = PointAttributeConvert.INSTANCE.convert2(attribute);
                pointAttributeService.createPointAttribute(createReqVO);
            }
        }

        for (String name : oldPointAttributeMap.keySet()) {
            if (!newPointAttributeMap.containsKey(name)) {
                try {
                    pointInfoService.selectByAttributeId(oldPointAttributeMap.get(name).getId());
                    throw new ServiceException(POINT_ATTRIBUTE_USED_CANNOT_DELETED);
                } catch (NotFoundException notFoundException1) {
                    log.debug("Point attribute is redundant, deleting: {}", oldPointAttributeMap.get(name));
                    pointAttributeService.deletePointAttribute(oldPointAttributeMap.get(name).getId());
                }
            }
        }
    }

}
