package cn.iocoder.yudao.module.system.dal.dataobject.iot.point;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 设备位号 DO
 *
 * @author 益莲科技
 */
@TableName("system_point")
@KeySequence("system_point_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 位号名称
     */
    private String name;
    /**
     * 数据类型：stringintdoublefloatlongoolean
     *
     * 枚举 {@link cn.iocoder.yudao.module.infra.enums.DictTypeConstants#CONFIG_TYPE 对应的类}
     */
    private String type;
    /**
     * 读写标识：0读，1写，2读写
     *
     * 枚举 {@link  cn.iocoder.yudao.module.system.enums.DictTypeConstants#OPERATE_TYPE 对应的类}
     */
    private Integer rw;
    /**
     * 基础值
     */
    private Float base;
    /**
     * 最小值
     */
    private Float minimum;
    /**
     * 最大值
     */
    private Float maximum;
    /**
     * 倍数
     */
    private Float multiple;
    /**
     * 累计标识
     */
    private Integer accrue;
    /**
     * 格式数据，Jave格式 %.3f
     */
    private String format;
    /**
     * 单位
     */
    private String unit;
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
     * 模板ID
     */
    private Long profileId;
    /**
     * 描述
     */
    private String description;
    /**
     * 租户ID
     */
    private Long tenantId;

}
