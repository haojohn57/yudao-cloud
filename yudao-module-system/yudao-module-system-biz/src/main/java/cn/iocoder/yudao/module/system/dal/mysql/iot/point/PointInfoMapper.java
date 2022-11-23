package cn.iocoder.yudao.module.system.dal.mysql.iot.point;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointinfo.vo.PointInfoExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointinfo.vo.PointInfoPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.point.PointInfoDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 位号配置信息 Mapper
 *
 * @author 益莲科技
 */
@Mapper
public interface PointInfoMapper extends BaseMapperX<PointInfoDO> {

    default PageResult<PointInfoDO> selectPage(PointInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PointInfoDO>()
                .eqIfPresent(PointInfoDO::getPointAttributeId, reqVO.getPointAttributeId())
                .eqIfPresent(PointInfoDO::getValue, reqVO.getValue())
                .eqIfPresent(PointInfoDO::getDeviceId, reqVO.getDeviceId())
                .eqIfPresent(PointInfoDO::getPointId, reqVO.getPointId())
                .betweenIfPresent(PointInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PointInfoDO::getId));
    }

    default List<PointInfoDO> selectList(PointInfoExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<PointInfoDO>()
                .eqIfPresent(PointInfoDO::getPointAttributeId, reqVO.getPointAttributeId())
                .eqIfPresent(PointInfoDO::getValue, reqVO.getValue())
                .eqIfPresent(PointInfoDO::getDeviceId, reqVO.getDeviceId())
                .eqIfPresent(PointInfoDO::getPointId, reqVO.getPointId())
                .betweenIfPresent(PointInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PointInfoDO::getId));
    }

}
