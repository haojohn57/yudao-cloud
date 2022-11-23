package cn.iocoder.yudao.module.system.dal.mysql.iot.driver;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverattribute.vo.DriverAttributeExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverattribute.vo.DriverAttributePageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.driver.DriverAttributeDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 连接配置信息 Mapper
 *
 * @author 益莲科技
 */
@Mapper
public interface DriverAttributeMapper extends BaseMapperX<DriverAttributeDO> {

    default PageResult<DriverAttributeDO> selectPage(DriverAttributePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DriverAttributeDO>()
                .likeIfPresent(DriverAttributeDO::getDisplayName, reqVO.getDisplayName())
                .likeIfPresent(DriverAttributeDO::getName, reqVO.getName())
                .eqIfPresent(DriverAttributeDO::getType, reqVO.getType())
                .eqIfPresent(DriverAttributeDO::getValue, reqVO.getValue())
                .eqIfPresent(DriverAttributeDO::getDriverId, reqVO.getDriverId())
                .betweenIfPresent(DriverAttributeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DriverAttributeDO::getId));
    }

    default List<DriverAttributeDO> selectList(DriverAttributeExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DriverAttributeDO>()
                .likeIfPresent(DriverAttributeDO::getDisplayName, reqVO.getDisplayName())
                .likeIfPresent(DriverAttributeDO::getName, reqVO.getName())
                .eqIfPresent(DriverAttributeDO::getType, reqVO.getType())
                .eqIfPresent(DriverAttributeDO::getValue, reqVO.getValue())
                .eqIfPresent(DriverAttributeDO::getDriverId, reqVO.getDriverId())
                .betweenIfPresent(DriverAttributeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DriverAttributeDO::getId));
    }

}
