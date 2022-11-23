package cn.iocoder.yudao.module.system.convert.iot.driver;

import cn.iocoder.yudao.framework.common.iot.model.DriverAttribute;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverattribute.vo.DriverAttributeCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverattribute.vo.DriverAttributeExcelVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverattribute.vo.DriverAttributeRespVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverattribute.vo.DriverAttributeUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.driver.DriverAttributeDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

/**
 * 连接配置信息 Convert
 *
 * @author 益莲科技
 */
@Mapper
public interface DriverAttributeConvert {

    DriverAttributeConvert INSTANCE = Mappers.getMapper(DriverAttributeConvert.class);

    DriverAttributeDO convert(DriverAttributeCreateReqVO bean);

    DriverAttributeDO convert(DriverAttributeUpdateReqVO bean);

    DriverAttributeRespVO convert(DriverAttributeDO bean);

    List<DriverAttributeRespVO> convertList(List<DriverAttributeDO> list);

    PageResult<DriverAttributeRespVO> convertPage(PageResult<DriverAttributeDO> page);

    List<DriverAttributeExcelVO> convertList02(List<DriverAttributeDO> list);

    Map<Long, DriverAttribute> convertMap(Map<Long, DriverAttributeDO> Map);

    DriverAttributeUpdateReqVO convert1(DriverAttribute bean);

    DriverAttributeCreateReqVO convert2(DriverAttribute bean);

    DriverAttribute convert3(DriverAttributeDO bean);
}
