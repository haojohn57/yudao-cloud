package cn.iocoder.yudao.module.system.dal.mysql.iot.point;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.iot.point.vo.PointExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.point.vo.PointPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.point.PointDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 设备位号 Mapper
 *
 * @author 益莲科技
 */
@Mapper
public interface PointMapper extends BaseMapperX<PointDO> {

    default PageResult<PointDO> selectPage(PointPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PointDO>()
                .likeIfPresent(PointDO::getName, reqVO.getName())
                .eqIfPresent(PointDO::getType, reqVO.getType())
                .eqIfPresent(PointDO::getRw, reqVO.getRw())
                .eqIfPresent(PointDO::getBase, reqVO.getBase())
                .eqIfPresent(PointDO::getMinimum, reqVO.getMinimum())
                .eqIfPresent(PointDO::getMaximum, reqVO.getMaximum())
                .eqIfPresent(PointDO::getMultiple, reqVO.getMultiple())
                .eqIfPresent(PointDO::getAccrue, reqVO.getAccrue())
                .eqIfPresent(PointDO::getFormat, reqVO.getFormat())
                .eqIfPresent(PointDO::getUnit, reqVO.getUnit())
                .eqIfPresent(PointDO::getEnable, reqVO.getEnable())
                .eqIfPresent(PointDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(PointDO::getProfileId, reqVO.getProfileId())
                .betweenIfPresent(PointDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PointDO::getId));
    }

    default List<PointDO> selectList(PointExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<PointDO>()
                .likeIfPresent(PointDO::getName, reqVO.getName())
                .eqIfPresent(PointDO::getType, reqVO.getType())
                .eqIfPresent(PointDO::getRw, reqVO.getRw())
                .eqIfPresent(PointDO::getBase, reqVO.getBase())
                .eqIfPresent(PointDO::getMinimum, reqVO.getMinimum())
                .eqIfPresent(PointDO::getMaximum, reqVO.getMaximum())
                .eqIfPresent(PointDO::getMultiple, reqVO.getMultiple())
                .eqIfPresent(PointDO::getAccrue, reqVO.getAccrue())
                .eqIfPresent(PointDO::getFormat, reqVO.getFormat())
                .eqIfPresent(PointDO::getUnit, reqVO.getUnit())
                .eqIfPresent(PointDO::getEnable, reqVO.getEnable())
                .eqIfPresent(PointDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(PointDO::getProfileId, reqVO.getProfileId())
                .betweenIfPresent(PointDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PointDO::getId));
    }

}
