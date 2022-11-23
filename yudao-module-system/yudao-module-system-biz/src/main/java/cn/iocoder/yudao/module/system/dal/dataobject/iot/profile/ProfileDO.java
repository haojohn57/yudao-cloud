package cn.iocoder.yudao.module.system.dal.dataobject.iot.profile;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * 设备模板 DO
 *
 * @author 益莲科技
 */
@TableName("system_profile")
@KeySequence("system_profile_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 模板名称
     */
    private String name;
    /**
     * 公有/私有模板标识
     */
    private Integer share;
    /**
     * 系统创建/用户创建/驱动创建
     */
    private Integer type;
    /**
     * 是否可用
     *
     * 枚举 {@link cn.iocoder.yudao.module.system.enums.DictTypeConstants#COMMON_STATUS 对应的类}
     */
    private Integer enable;
    /**
     * 部门ID
     */
    private Long deptId;
    /**
     * 设备ID
     */
    private Long deviceId;
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 描述
     */
    private String description;

    @TableField(exist = false)
    private Set<Long> pointIds = new HashSet<>(8);

    public ProfileDO(String name, Integer share, Long driverId, Long tenantId) {
        this.name = name;
        this.share = share;
        this.tenantId = tenantId;
    }

}
