package cn.iocoder.yudao.module.system.dal.mysql.iot.driver;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.iot.driver.vo.DriverExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.driver.vo.DriverPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.driver.DriverDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 协议驱动 Mapper
 *
 * @author 益莲科技
 */
@Mapper
public interface DriverMapper extends BaseMapperX<DriverDO> {

    default PageResult<DriverDO> selectPage(DriverPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DriverDO>()
                .likeIfPresent(DriverDO::getName, reqVO.getName())
                .likeIfPresent(DriverDO::getServiceName, reqVO.getServiceName())
                .eqIfPresent(DriverDO::getType, reqVO.getType())
                .eqIfPresent(DriverDO::getHost, reqVO.getHost())
                .eqIfPresent(DriverDO::getPort, reqVO.getPort())
                .eqIfPresent(DriverDO::getEnable, reqVO.getEnable())
                .betweenIfPresent(DriverDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DriverDO::getId));
    }

    default List<DriverDO> selectList(DriverExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DriverDO>()
                .likeIfPresent(DriverDO::getName, reqVO.getName())
                .likeIfPresent(DriverDO::getServiceName, reqVO.getServiceName())
                .eqIfPresent(DriverDO::getType, reqVO.getType())
                .eqIfPresent(DriverDO::getHost, reqVO.getHost())
                .eqIfPresent(DriverDO::getPort, reqVO.getPort())
                .eqIfPresent(DriverDO::getEnable, reqVO.getEnable())
                .betweenIfPresent(DriverDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DriverDO::getId));
    }

    default DriverDO selectById(Long id) {
        return selectOne(new LambdaQueryWrapperX<DriverDO>().eq(DriverDO::getId, id));
    }

}
