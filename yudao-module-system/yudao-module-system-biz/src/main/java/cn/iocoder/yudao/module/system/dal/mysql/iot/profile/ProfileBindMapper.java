package cn.iocoder.yudao.module.system.dal.mysql.iot.profile;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.system.controller.admin.iot.profilebind.vo.ProfileBindExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.profilebind.vo.ProfileBindPageReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.profile.ProfileBindDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 设备与模版映射关联 Mapper
 *
 * @author 益莲科技
 */
@Mapper
public interface ProfileBindMapper extends BaseMapperX<ProfileBindDO> {

    default PageResult<ProfileBindDO> selectPage(ProfileBindPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProfileBindDO>()
                .eqIfPresent(ProfileBindDO::getProfileId, reqVO.getProfileId())
                .eqIfPresent(ProfileBindDO::getDeviceId, reqVO.getDeviceId())
                .betweenIfPresent(ProfileBindDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProfileBindDO::getId));
    }

    default List<ProfileBindDO> selectList(ProfileBindExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ProfileBindDO>()
                .eqIfPresent(ProfileBindDO::getProfileId, reqVO.getProfileId())
                .eqIfPresent(ProfileBindDO::getDeviceId, reqVO.getDeviceId())
                .betweenIfPresent(ProfileBindDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProfileBindDO::getId));
    }

}
