package cn.iocoder.yudao.module.system.convert.iot.point;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointinfo.vo.PointInfoCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointinfo.vo.PointInfoExcelVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointinfo.vo.PointInfoRespVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointinfo.vo.PointInfoUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.point.PointInfoDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 位号配置信息 Convert
 *
 * @author 益莲科技
 */
@Mapper
public interface PointInfoConvert {

    PointInfoConvert INSTANCE = Mappers.getMapper(PointInfoConvert.class);

    PointInfoDO convert(PointInfoCreateReqVO bean);

    PointInfoDO convert(PointInfoUpdateReqVO bean);

    PointInfoRespVO convert(PointInfoDO bean);

    List<PointInfoRespVO> convertList(List<PointInfoDO> list);

    PageResult<PointInfoRespVO> convertPage(PageResult<PointInfoDO> page);

    List<PointInfoExcelVO> convertList02(List<PointInfoDO> list);

}
