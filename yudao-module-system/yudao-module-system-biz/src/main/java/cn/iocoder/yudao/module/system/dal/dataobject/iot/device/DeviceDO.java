package cn.iocoder.yudao.module.system.dal.dataobject.iot.device;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * 设备 DO
 *
 * @author 益莲科技
 */
@TableName("system_device")
@KeySequence("system_device_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 设备名称
     */
    private String name;
    /**
     * 位号数据是否结构化
     */
    private Integer multi;
    /**
     * 设备状态
     *
     * 枚举 {@link cn.iocoder.yudao.module.system.enums.DictTypeConstants#COMMON_STATUS 对应的类}
     */
    private Integer status;
    /**
     * 设备分类
     *
     * 枚举 {@link cn.iocoder.yudao.module.system.enums.DictTypeConstants#DEVICE_CATEGORY 对应的类}
     */
    private Integer category;
    /**
     * 驱动ID
     */
    private Long driverId;
    /**
     * 部门ID
     */
    private Long deptId;
    /**
     * 租户ID
     */
    private Long tenantId;
    /**
     * 描述
     */
    private String description;

    @TableField(exist = false)
    private Set<Long> profileIds = new HashSet<>(8);

}
