package cn.iocoder.yudao.module.system.convert.iot.profile;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.iot.profile.vo.ProfileCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.profile.vo.ProfileExcelVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.profile.vo.ProfileRespVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.profile.vo.ProfileUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.profile.ProfileDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 设备模板 Convert
 *
 * @author 益莲科技
 */
@Mapper
public interface ProfileConvert {

    ProfileConvert INSTANCE = Mappers.getMapper(ProfileConvert.class);

    ProfileDO convert(ProfileCreateReqVO bean);

    ProfileDO convert(ProfileUpdateReqVO bean);

    ProfileRespVO convert(ProfileDO bean);

    ProfileUpdateReqVO convert1(ProfileDO bean);

    List<ProfileRespVO> convertList(List<ProfileDO> list);

    PageResult<ProfileRespVO> convertPage(PageResult<ProfileDO> page);

    List<ProfileExcelVO> convertList02(List<ProfileDO> list);

    ProfileCreateReqVO convert2(ProfileDO bean);
}
