package cn.iocoder.yudao.module.system.service.iot.profile;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.iot.profile.vo.ProfileCreateReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.profile.vo.ProfileExportReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.profile.vo.ProfilePageReqVO;
import cn.iocoder.yudao.module.system.controller.admin.iot.profile.vo.ProfileUpdateReqVO;
import cn.iocoder.yudao.module.system.dal.dataobject.iot.profile.ProfileDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 设备模板 Service 接口
 *
 * @author 益莲科技
 */
public interface ProfileService {

    /**
     * 创建设备模板
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProfile(@Valid ProfileCreateReqVO createReqVO);

    /**
     * 更新设备模板
     *
     * @param updateReqVO 更新信息
     */
    void updateProfile(@Valid ProfileUpdateReqVO updateReqVO);

    /**
     * 删除设备模板
     *
     * @param id 编号
     */
    void deleteProfile(Long id);

    /**
     * 获得设备模板
     *
     * @param id 编号
     * @return 设备模板
     */
    ProfileDO getProfile(Long id);

    /**
     * 获得设备模板列表
     *
     * @param ids 编号
     * @return 设备模板列表
     */
    List<ProfileDO> getProfileList(Collection<Long> ids);

    /**
     * 获得设备模板分页
     *
     * @param pageReqVO 分页查询
     * @return 设备模板分页
     */
    PageResult<ProfileDO> getProfilePage(ProfilePageReqVO pageReqVO);

    /**
     * 获得设备模板列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 设备模板列表
     */
    List<ProfileDO> getProfileList(ProfileExportReqVO exportReqVO);
    /**
     * 根据 模板Name 查询模版
     *
     * @param name Profile Name
     * @param type Profile Type
     * @return Profile
     */
    ProfileDO selectByNameAndType(String name, Short type, Long tenantId);

    /**
     * 根据 模版Id集 查询模版
     *
     * @param ids Profile Id Set
     * @return Profile Array
     */
    List<ProfileDO> selectByIds(Set<Long> ids);

    /**
     * 根据 设备Id 查询模版
     *
     * @param deviceId Device Id
     * @return Profile Array
     */
    List<ProfileDO> selectByDeviceId(Long deviceId);

    ProfileDO selectById(Long id);
}
