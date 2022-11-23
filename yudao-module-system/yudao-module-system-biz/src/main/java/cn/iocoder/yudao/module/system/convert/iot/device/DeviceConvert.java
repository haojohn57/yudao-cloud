package cn.iocoder.yudao.module.system.convert.iot.device;

import cn.iocoder.yudao.framework.common.iot.model.Device;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.api.iot.device.dto.DeviceRespDTO;
import cn.iocoder.yudao.module.system.controller.admin.iot.device.vo.DeviceCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.device.vo.DeviceExcelVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.device.vo.DeviceRespVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.device.vo.DeviceUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.device.DeviceDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

/**
 * 设备 Convert
 *
 * @author 益莲科技
 */
@Mapper
public interface DeviceConvert {

    DeviceConvert INSTANCE = Mappers.getMapper(DeviceConvert.class);

    DeviceDO convert(DeviceCreateReqVO bean);

    DeviceDO convert(DeviceUpdateReqVO bean);

    DeviceRespVO convert(DeviceDO bean);

    List<DeviceRespVO> convertList(List<DeviceDO> list);

    PageResult<DeviceRespVO> convertPage(PageResult<DeviceDO> page);

    List<DeviceExcelVO> convertList02(List<DeviceDO> list);

    Map<Long, Device> convertMap(Map<Long, DeviceDO> map);

    Device convert1(DeviceDO bean);

    List<Device> convertList01(List<DeviceDO> list);

    DeviceRespDTO convert3(DeviceDO bean);

    List<DeviceRespDTO> convertList03(List<DeviceDO> list);
}
