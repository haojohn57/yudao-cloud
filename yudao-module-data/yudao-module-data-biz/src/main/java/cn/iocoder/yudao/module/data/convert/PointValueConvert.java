package cn.iocoder.yudao.module.data.convert;

import cn.iocoder.yudao.framework.common.iot.bean.point.PointValue;
import cn.iocoder.yudao.module.data.controller.admin.vo.PointValueRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 设备位号值 Convert
 *
 * @author 益莲科技
 */
@Mapper
public interface PointValueConvert {

    PointValueConvert INSTANCE = Mappers.getMapper(PointValueConvert.class);

    List<PointValueRespVO> convertList(List<PointValue> list);

}
