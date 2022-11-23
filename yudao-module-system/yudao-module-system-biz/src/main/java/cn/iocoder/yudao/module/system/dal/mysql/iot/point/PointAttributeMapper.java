package cn.iocoder.yudao.module.system.dal.mysql.iot.point;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointattribute.vo.PointAttributeExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.pointattribute.vo.PointAttributePageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.point.PointAttributeDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 位号配置信息 Mapper
 *
 * @author 益莲科技
 */
@Mapper
public interface PointAttributeMapper extends BaseMapperX<PointAttributeDO> {

    default PageResult<PointAttributeDO> selectPage(PointAttributePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PointAttributeDO>()
                .likeIfPresent(PointAttributeDO::getDisplayName, reqVO.getDisplayName())
                .likeIfPresent(PointAttributeDO::getName, reqVO.getName())
                .eqIfPresent(PointAttributeDO::getType, reqVO.getType())
                .eqIfPresent(PointAttributeDO::getValue, reqVO.getValue())
                .eqIfPresent(PointAttributeDO::getDriverId, reqVO.getDriverId())
                .betweenIfPresent(PointAttributeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PointAttributeDO::getId));
    }

    default List<PointAttributeDO> selectList(PointAttributeExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<PointAttributeDO>()
                .likeIfPresent(PointAttributeDO::getDisplayName, reqVO.getDisplayName())
                .likeIfPresent(PointAttributeDO::getName, reqVO.getName())
                .eqIfPresent(PointAttributeDO::getType, reqVO.getType())
                .eqIfPresent(PointAttributeDO::getValue, reqVO.getValue())
                .eqIfPresent(PointAttributeDO::getDriverId, reqVO.getDriverId())
                .betweenIfPresent(PointAttributeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PointAttributeDO::getId));
    }

}
