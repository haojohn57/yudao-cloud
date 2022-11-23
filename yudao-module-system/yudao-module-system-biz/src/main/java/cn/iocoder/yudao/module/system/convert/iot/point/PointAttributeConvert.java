package cn.iocoder.yudao.module.system.convert.iot.point;

import cn.iocoder.yudao.framework.common.iot.model.PointAttribute;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointattribute.vo.PointAttributeCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointattribute.vo.PointAttributeExcelVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointattribute.vo.PointAttributeRespVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointattribute.vo.PointAttributeUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.point.PointAttributeDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

/**
 * 位号配置信息 Convert
 *
 * @author 益莲科技
 */
@Mapper
public interface PointAttributeConvert {

    PointAttributeConvert INSTANCE = Mappers.getMapper(PointAttributeConvert.class);

    PointAttributeDO convert(PointAttributeCreateReqVO bean);

    PointAttributeDO convert(PointAttributeUpdateReqVO bean);

    PointAttributeRespVO convert(PointAttributeDO bean);

    List<PointAttributeRespVO> convertList(List<PointAttributeDO> list);

    PageResult<PointAttributeRespVO> convertPage(PageResult<PointAttributeDO> page);

    List<PointAttributeExcelVO> convertList02(List<PointAttributeDO> list);

    PointAttributeUpdateReqVO convert1(PointAttribute bean);

    PointAttributeCreateReqVO convert2(PointAttribute bean);

    Map<Long, PointAttribute> convertMap(Map<Long, PointAttributeDO> pointAttributeMap);
}
