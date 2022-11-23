package cn.iocoder.yudao.module.system.convert.iot.point;

import cn.iocoder.yudao.framework.common.iot.model.Point;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.api.iot.point.dto.PointRespDTO;
import cn.iocoder.yudao.module.system.controller.admin.iot.point.vo.PointCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.point.vo.PointExcelVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.point.vo.PointRespVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.point.vo.PointUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.point.PointDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 设备位号 Convert
 *
 * @author 益莲科技
 */
@Mapper
public interface PointConvert {

    PointConvert INSTANCE = Mappers.getMapper(PointConvert.class);

    PointDO convert(PointCreateReqVO bean);

    PointDO convert(PointUpdateReqVO bean);

    PointRespVO convert(PointDO bean);

    List<PointRespVO> convertList(List<PointDO> list);

    PageResult<PointRespVO> convertPage(PageResult<PointDO> page);

    List<PointExcelVO> convertList02(List<PointDO> list);

    PointUpdateReqVO convert1(PointDO bean);

    PointCreateReqVO convert2(PointDO bean);

    Point convert3(PointDO bean);

    PointRespDTO convert4(PointDO bean);

    List<PointRespDTO> convertList03(List<PointDO> list);
}
