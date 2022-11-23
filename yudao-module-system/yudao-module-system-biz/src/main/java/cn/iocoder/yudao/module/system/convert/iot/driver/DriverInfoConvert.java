package cn.iocoder.yudao.module.system.convert.iot.driver;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverinfo.vo.DriverInfoCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverinfo.vo.DriverInfoExcelVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverinfo.vo.DriverInfoRespVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverinfo.vo.DriverInfoUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.driver.DriverInfoDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 协议配置信息 Convert
 *
 * @author 益莲科技
 */
@Mapper
public interface DriverInfoConvert {

    DriverInfoConvert INSTANCE = Mappers.getMapper(DriverInfoConvert.class);

    DriverInfoDO convert(DriverInfoCreateReqVO bean);

    DriverInfoDO convert(DriverInfoUpdateReqVO bean);

    DriverInfoRespVO convert(DriverInfoDO bean);

    DriverInfoUpdateReqVO convert1(DriverInfoDO bean);

    DriverInfoCreateReqVO convert2(DriverInfoDO bean);

    List<DriverInfoRespVO> convertList(List<DriverInfoDO> list);

    PageResult<DriverInfoRespVO> convertPage(PageResult<DriverInfoDO> page);

    List<DriverInfoExcelVO> convertList02(List<DriverInfoDO> list);

}
