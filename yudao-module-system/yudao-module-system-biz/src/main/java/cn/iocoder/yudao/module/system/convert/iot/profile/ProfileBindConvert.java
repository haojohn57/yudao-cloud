package cn.iocoder.yudao.module.system.convert.iot.profile;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.iot.profilebind.vo.ProfileBindCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.profilebind.vo.ProfileBindExcelVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.profilebind.vo.ProfileBindRespVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.profilebind.vo.ProfileBindUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.profile.ProfileBindDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 设备与模版映射关联 Convert
 *
 * @author 益莲科技
 */
@Mapper
public interface ProfileBindConvert {

    ProfileBindConvert INSTANCE = Mappers.getMapper(ProfileBindConvert.class);

    ProfileBindDO convert(ProfileBindCreateReqVO bean);

    ProfileBindDO convert(ProfileBindUpdateReqVO bean);

    ProfileBindRespVO convert(ProfileBindDO bean);

    List<ProfileBindRespVO> convertList(List<ProfileBindDO> list);

    PageResult<ProfileBindRespVO> convertPage(PageResult<ProfileBindDO> page);

    List<ProfileBindExcelVO> convertList02(List<ProfileBindDO> list);

}
