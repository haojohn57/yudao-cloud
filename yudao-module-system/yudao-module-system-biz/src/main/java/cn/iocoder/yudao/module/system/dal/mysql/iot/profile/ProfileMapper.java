package cn.iocoder.yudao.module.system.dal.mysql.iot.profile;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.iot.profile.vo.ProfileExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.profile.vo.ProfilePageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.profile.ProfileDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 设备模板 Mapper
 *
 * @author 益莲科技
 */
@Mapper
public interface ProfileMapper extends BaseMapperX<ProfileDO> {

    default PageResult<ProfileDO> selectPage(ProfilePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProfileDO>()
                .likeIfPresent(ProfileDO::getName, reqVO.getName())
                .eqIfPresent(ProfileDO::getShare, reqVO.getShare())
                .eqIfPresent(ProfileDO::getType, reqVO.getType())
                .eqIfPresent(ProfileDO::getEnable, reqVO.getEnable())
                .eqIfPresent(ProfileDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(ProfileDO::getDeviceId, reqVO.getDeviceId())
                .betweenIfPresent(ProfileDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProfileDO::getId));
    }

    default List<ProfileDO> selectList(ProfileExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ProfileDO>()
                .likeIfPresent(ProfileDO::getName, reqVO.getName())
                .eqIfPresent(ProfileDO::getShare, reqVO.getShare())
                .eqIfPresent(ProfileDO::getType, reqVO.getType())
                .eqIfPresent(ProfileDO::getEnable, reqVO.getEnable())
                .eqIfPresent(ProfileDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(ProfileDO::getDeviceId, reqVO.getDeviceId())
                .betweenIfPresent(ProfileDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProfileDO::getId));
    }

}
