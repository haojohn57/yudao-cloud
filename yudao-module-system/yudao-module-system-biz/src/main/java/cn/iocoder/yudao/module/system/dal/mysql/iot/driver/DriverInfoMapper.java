package cn.iocoder.yudao.module.system.dal.mysql.iot.driver;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverinfo.vo.DriverInfoExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driverinfo.vo.DriverInfoPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.driver.DriverInfoDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 协议配置信息 Mapper
 *
 * @author 益莲科技
 */
@Mapper
public interface DriverInfoMapper extends BaseMapperX<DriverInfoDO> {

    default PageResult<DriverInfoDO> selectPage(DriverInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DriverInfoDO>()
                .eqIfPresent(DriverInfoDO::getDriverAttributeId, reqVO.getDriverAttributeId())
                .eqIfPresent(DriverInfoDO::getValue, reqVO.getValue())
                .eqIfPresent(DriverInfoDO::getDeviceId, reqVO.getDeviceId())
                .betweenIfPresent(DriverInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DriverInfoDO::getId));
    }

    default List<DriverInfoDO> selectList(DriverInfoExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DriverInfoDO>()
                .eqIfPresent(DriverInfoDO::getDriverAttributeId, reqVO.getDriverAttributeId())
                .eqIfPresent(DriverInfoDO::getValue, reqVO.getValue())
                .eqIfPresent(DriverInfoDO::getDeviceId, reqVO.getDeviceId())
                .betweenIfPresent(DriverInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DriverInfoDO::getId));
    }

}
