package cn.iocoder.yudao.module.system.service.iot.profilebind;

import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.framework.common.exception.NotFoundException;
import cn.iocoder.yudao.framework.common.exception.ServiceException;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.iot.profilebind.vo.ProfileBindCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.profilebind.vo.ProfileBindExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.profilebind.vo.ProfileBindPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.profilebind.vo.ProfileBindUpdateReqVO;
import cn.iocoder.yudao.module.system.convert.iot.profile.ProfileBindConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.profile.ProfileBindDO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.profile.ProfileDO;
import cn.iocoder.yudao.module.system.dal.mysql.iot.profile.ProfileBindMapper;
import cn.iocoder.yudao.module.system.dal.mysql.iot.profile.ProfileMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.PROFILE_BIND_NOT_EXISTS;

/**
 * 设备与模版映射关联 Service 实现类
 *
 * @author 益莲科技
 */
@Service
@Validated
public class ProfileBindServiceImpl implements ProfileBindService {

    @Resource
    private ProfileBindMapper profileBindMapper;
    @Resource
    private ProfileMapper profileMapper;

    @Override
    public Long createProfileBind(ProfileBindCreateReqVO createReqVO) {
        // 插入
        ProfileBindDO profileBind = ProfileBindConvert.INSTANCE.convert(createReqVO);
        profileBindMapper.insert(profileBind);
        // 返回
        return profileBind.getId();
    }

    @Override
    public void updateProfileBind(ProfileBindUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateProfileBindExists(updateReqVO.getId());
        // 更新
        ProfileBindDO updateObj = ProfileBindConvert.INSTANCE.convert(updateReqVO);
        profileBindMapper.updateById(updateObj);
    }

    @Override
    public void deleteProfileBind(Long id) {
        // 校验存在
        this.validateProfileBindExists(id);
        // 删除
        profileBindMapper.deleteById(id);
    }

    private void validateProfileBindExists(Long id) {
        if (profileBindMapper.selectById(id) == null) {
            throw new ServiceException(PROFILE_BIND_NOT_EXISTS);
        }
    }

    @Override
    public ProfileBindDO getProfileBind(Long id) {
        return profileBindMapper.selectById(id);
    }

    @Override
    public List<ProfileBindDO> getProfileBindList(Collection<Long> ids) {
        return profileBindMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<ProfileBindDO> getProfileBindPage(ProfileBindPageReqVO pageReqVO) {
        return profileBindMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ProfileBindDO> getProfileBindList(ProfileBindExportReqVO exportReqVO) {
        return profileBindMapper.selectList(exportReqVO);
    }

    @Override
    public List<ProfileBindDO> addByDeviceId(Long deviceId, Set<Long> profileIds) {
        List<ProfileBindDO> profileBinds = new ArrayList<>();
        if (null != profileIds) {
            profileIds.forEach(profileId -> {
                ProfileDO profile = profileMapper.selectById(profileId);
                if (ObjectUtil.isNotNull(profile)) {
                    ProfileBindCreateReqVO createReqVO = new ProfileBindCreateReqVO();
                    createReqVO.setDeviceId(deviceId);
                    createReqVO.setProfileId(profileId);
                    ProfileBindDO profileBind = getProfileBind(createProfileBind(createReqVO));
                    profileBinds.add(profileBind);
                }
            });
        }
        return profileBinds;
    }

    @Override
    public boolean deleteByDeviceId(Long deviceId) {
        Set<Long> profileIds =selectProfileIdByDeviceId(deviceId);
        if (null != profileIds) {
            profileIds.forEach(profileId -> {
                LambdaQueryWrapper<ProfileBindDO> queryWrapper = Wrappers.<ProfileBindDO>query().lambda();
                queryWrapper.eq(ProfileBindDO::getProfileId, profileId);
                ProfileBindDO profileBind = profileBindMapper.selectOne(queryWrapper);
                deleteProfileBind(profileBind.getId());
            });
        }
        return true;  //Todo
    }

    @Override
    public boolean deleteByProfileIdAndDeviceId(Long deviceId, Long profileId) {
        ProfileBindDO profileBindDO = selectByDeviceIdAndProfileId(deviceId,profileId);
        deleteProfileBind(profileBindDO.getId());
        return true;  //Todo
    }

    @Override
    public ProfileBindDO selectById(Long id) {
        ProfileBindDO profileBind = profileBindMapper.selectById(id);
        if (null == profileBind) {
            throw new NotFoundException("The profile bind does not exist");
        }
        return profileBind;
    }

    @Override
    public ProfileBindDO selectByDeviceIdAndProfileId(Long deviceId, Long profileId) {
        LambdaQueryWrapper<ProfileBindDO> queryWrapper = Wrappers.<ProfileBindDO>query().lambda();
        queryWrapper.eq(ProfileBindDO::getProfileId, profileId);
        queryWrapper.eq(ProfileBindDO::getDeviceId, deviceId);
        ProfileBindDO profileBind = profileBindMapper.selectOne(queryWrapper);
        if (null == profileBind) {
            throw new NotFoundException("The profile bind does not exist");
        }
        return profileBind;
    }

    @Override
    public Set<Long> selectDeviceIdByProfileId(Long profileId) {
        ProfileBindExportReqVO exportReqVO = new ProfileBindExportReqVO();
        exportReqVO.setProfileId(profileId);
        List<ProfileBindDO> profileBinds = profileBindMapper.selectList(exportReqVO);
        return profileBinds.stream().map(ProfileBindDO::getDeviceId).collect(Collectors.toSet());
    }

    @Override
    public Set<Long> selectProfileIdByDeviceId(Long deviceId) {
        ProfileBindExportReqVO exportReqVO = new ProfileBindExportReqVO();
        exportReqVO.setDeviceId(deviceId);
        List<ProfileBindDO> profileBinds = profileBindMapper.selectList(exportReqVO);
        return profileBinds.stream().map(ProfileBindDO::getProfileId).collect(Collectors.toSet());
    }
}
