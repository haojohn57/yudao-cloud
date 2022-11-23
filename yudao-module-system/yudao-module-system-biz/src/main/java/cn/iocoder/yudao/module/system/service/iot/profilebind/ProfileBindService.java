package cn.iocoder.yudao.module.system.service.iot.profilebind;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.iot.profilebind.vo.ProfileBindCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.profilebind.vo.ProfileBindExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.profilebind.vo.ProfileBindPageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.profilebind.vo.ProfileBindUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.profile.ProfileBindDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 设备与模版映射关联 Service 接口
 *
 * @author 益莲科技
 */
public interface ProfileBindService {

    /**
     * 创建设备与模版映射关联
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProfileBind(@Valid ProfileBindCreateReqVO createReqVO);

    /**
     * 更新设备与模版映射关联
     *
     * @param updateReqVO 更新信息
     */
    void updateProfileBind(@Valid ProfileBindUpdateReqVO updateReqVO);

    /**
     * 删除设备与模版映射关联
     *
     * @param id 编号
     */
    void deleteProfileBind(Long id);

    /**
     * 获得设备与模版映射关联
     *
     * @param id 编号
     * @return 设备与模版映射关联
     */
    ProfileBindDO getProfileBind(Long id);

    /**
     * 获得设备与模版映射关联列表
     *
     * @param ids 编号
     * @return 设备与模版映射关联列表
     */
    List<ProfileBindDO> getProfileBindList(Collection<Long> ids);

    /**
     * 获得设备与模版映射关联分页
     *
     * @param pageReqVO 分页查询
     * @return 设备与模版映射关联分页
     */
    PageResult<ProfileBindDO> getProfileBindPage(ProfileBindPageReqVO pageReqVO);

    /**
     * 获得设备与模版映射关联列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 设备与模版映射关联列表
     */
    List<ProfileBindDO> getProfileBindList(ProfileBindExportReqVO exportReqVO);

    /**
     * 根据 设备ID 新增关联的模版映射
     *
     * @param deviceId   Device Id
     * @param profileIds Profile Id Set
     * @return ProfileBind Array
     */
    List<ProfileBindDO> addByDeviceId(Long deviceId, Set<Long> profileIds);

    /**
     * 根据 设备ID 删除关联的模版映射
     *
     * @param deviceId Device Id
     * @return boolean
     */
    boolean deleteByDeviceId(Long deviceId);

    /**
     * 根据 设备ID 和 模版ID 删除关联的模版映射
     *
     * @param deviceId  Device Id
     * @param profileId Profile Id
     * @return boolean
     */
    boolean deleteByProfileIdAndDeviceId(Long deviceId, Long profileId);

    ProfileBindDO selectById(Long id);

    /**
     * 根据 设备ID 和 模版ID 查询关联的模版映射
     *
     * @param deviceId  Device Id
     * @param profileId Profile Id
     * @return ProfileBind
     */
    ProfileBindDO selectByDeviceIdAndProfileId(Long deviceId, Long profileId);

    /**
     * 根据 模版ID 查询关联的 设备ID 集合
     *
     * @param profileId Profile Id
     * @return Device Id Set
     */
    Set<Long> selectDeviceIdByProfileId(Long profileId);

    /**
     * 根据 设备ID 查询关联的 模版ID 集合
     *
     * @param deviceId Device Id
     * @return Profile Id Set
     */
    Set<Long> selectProfileIdByDeviceId(Long deviceId);

}
