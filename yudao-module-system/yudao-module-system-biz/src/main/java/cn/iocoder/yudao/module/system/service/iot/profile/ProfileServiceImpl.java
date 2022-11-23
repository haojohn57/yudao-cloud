package cn.iocoder.yudao.module.system.service.iot.profile;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.framework.common.exception.NotFoundException;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.iot.profile.vo.ProfileCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.profile.vo.ProfileExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.profile.vo.ProfilePageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.profile.vo.ProfileUpdateReqVO;
import cn.iocoder.yudao.module.system.convert.iot.profile.ProfileConvert;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.device.DeviceDO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.point.PointDO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.profile.ProfileDO;
import cn.iocoder.yudao.module.system.dal.mysql.iot.device.DeviceMapper;
import cn.iocoder.yudao.module.system.dal.mysql.iot.profile.ProfileMapper;
import cn.iocoder.yudao.module.system.service.iot.point.PointService;
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

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.PROFILE_NOT_EXISTS;

/**
 * 设备模板 Service 实现类
 *
 * @author 益莲科技
 */
@Service
@Validated
public class ProfileServiceImpl implements ProfileService {

    @Resource
    private ProfileMapper profileMapper;
    @Resource
    private PointService pointService;
    @Resource
    private DeviceMapper deviceMapper;


    @Override
    public Long createProfile(ProfileCreateReqVO createReqVO) {
        // 插入
        ProfileDO profile = ProfileConvert.INSTANCE.convert(createReqVO);
        profileMapper.insert(profile);
        // 返回
        return profile.getId();
    }

    @Override
    public void updateProfile(ProfileUpdateReqVO updateReqVO) {
        // 校验存在
        this.validateProfileExists(updateReqVO.getId());
        // 更新
        ProfileDO updateObj = ProfileConvert.INSTANCE.convert(updateReqVO);
        profileMapper.updateById(updateObj);
    }

    @Override
    public void deleteProfile(Long id) {
        // 校验存在
        this.validateProfileExists(id);
        // 删除
        profileMapper.deleteById(id);
    }

    private void validateProfileExists(Long id) {
        if (profileMapper.selectById(id) == null) {
            throw exception(PROFILE_NOT_EXISTS);
        }
    }

    @Override
    public ProfileDO getProfile(Long id) {
        return profileMapper.selectById(id);
    }

    @Override
    public List<ProfileDO> getProfileList(Collection<Long> ids) {
        return profileMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<ProfileDO> getProfilePage(ProfilePageReqVO pageReqVO) {
        return profileMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ProfileDO> getProfileList(ProfileExportReqVO exportReqVO) {
        return profileMapper.selectList(exportReqVO);
    }

    @Override
    public ProfileDO selectById(Long id) {
        ProfileDO profile = profileMapper.selectById(id);
        if (null == profile) {
            throw new NotFoundException("The profile does not exist");
        }
        try {
            profile.setPointIds(pointService.selectByProfileId(id).stream().map(PointDO::getId).collect(Collectors.toSet()));
        } catch (NotFoundException ignored) {
        }
        return profile;
    }

    @Override
    public ProfileDO selectByNameAndType(String name, Short type, Long tenantId) {
        LambdaQueryWrapper<ProfileDO> queryWrapper = Wrappers.<ProfileDO>query().lambda();
        queryWrapper.eq(ProfileDO::getName, name);
        queryWrapper.eq(ProfileDO::getType, type);
        queryWrapper.eq(ProfileDO::getTenantId, tenantId);
        ProfileDO profile = profileMapper.selectOne(queryWrapper);
        if (null == profile) {
            throw new NotFoundException("The profile does not exist");
        }
        try {
            profile.setPointIds(pointService.selectByProfileId(profile.getId()).stream().map(PointDO::getId).collect(Collectors.toSet()));
        } catch (NotFoundException ignored) {
        }
        return profile;
    }

    @Override
    public List<ProfileDO> selectByIds(Set<Long> ids) {
        List<ProfileDO> profiles = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(ids)) {
            profiles = profileMapper.selectBatchIds(ids);
            profiles.forEach(profile -> {
                try {
                    profile.setPointIds(pointService.selectByProfileId(profile.getId()).stream().map(PointDO::getId).collect(Collectors.toSet()));
                } catch (NotFoundException ignored) {
                }
            });
        }
        return profiles;
    }

    @Override
    public List<ProfileDO> selectByDeviceId(Long deviceId) {
        DeviceDO device = deviceMapper.selectById(deviceId);
        if (ObjectUtil.isNotNull(device)) {
            return selectByIds(device.getProfileIds());
        }
        return new ArrayList<>();
    }
}
