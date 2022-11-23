package cn.iocoder.yudao.module.system.dal.mysql.iot.device;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.iot.device.vo.DeviceExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.device.vo.DevicePageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.device.DeviceDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 设备 Mapper
 *
 * @author 益莲科技
 */
@Mapper
public interface DeviceMapper extends BaseMapperX<DeviceDO> {

    default PageResult<DeviceDO> selectPage(DevicePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DeviceDO>()
                .likeIfPresent(DeviceDO::getName, reqVO.getName())
                .eqIfPresent(DeviceDO::getMulti, reqVO.getMulti())
                .eqIfPresent(DeviceDO::getStatus, reqVO.getStatus())
                .eqIfPresent(DeviceDO::getCategory, reqVO.getCategory())
                .eqIfPresent(DeviceDO::getDriverId, reqVO.getDriverId())
                .eqIfPresent(DeviceDO::getDeptId, reqVO.getDeptId())
                .betweenIfPresent(DeviceDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DeviceDO::getId));
    }

    default List<DeviceDO> selectList(DeviceExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DeviceDO>()
                .likeIfPresent(DeviceDO::getName, reqVO.getName())
                .eqIfPresent(DeviceDO::getMulti, reqVO.getMulti())
                .eqIfPresent(DeviceDO::getStatus, reqVO.getStatus())
                .eqIfPresent(DeviceDO::getCategory, reqVO.getCategory())
                .eqIfPresent(DeviceDO::getDriverId, reqVO.getDriverId())
                .eqIfPresent(DeviceDO::getDeptId, reqVO.getDeptId())
                .betweenIfPresent(DeviceDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DeviceDO::getId));
    }

    default DeviceDO selectById(Long id) {
        return selectOne(new LambdaQueryWrapper<DeviceDO>().eq(DeviceDO::getId, id));
    }

}
