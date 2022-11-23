package cn.iocoder.yudao.module.system.convert.iot.driver;

import cn.iocoder.yudao.framework.common.iot.model.Driver;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.api.iot.driver.dto.DriverRespDTO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driver.vo.DriverCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driver.vo.DriverExcelVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driver.vo.DriverRespVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driver.vo.DriverUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.driver.DriverDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 协议驱动 Convert
 *
 * @author 益莲科技
 */
@Mapper
public interface DriverConvert {

    DriverConvert INSTANCE = Mappers.getMapper(DriverConvert.class);

    DriverDO convert(DriverCreateReqVO bean);

    DriverDO convert(DriverUpdateReqVO bean);

    DriverRespVO convert(DriverDO bean);

    DriverUpdateReqVO convert1(DriverDO bean);

    DriverCreateReqVO convert2(DriverDO bean);

    List<DriverRespVO> convertList(List<DriverDO> list);

    PageResult<DriverRespVO> convertPage(PageResult<DriverDO> page);

    List<DriverExcelVO> convertList02(List<DriverDO> list);

    DriverCreateReqVO convert3(Driver bean);

    DriverUpdateReqVO convert4(Driver bean);

    DriverRespDTO convert5(DriverDO bean);
}
