package cn.iocoder.yudao.module.system.dal.dataobject.iot.driver;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 协议驱动 DO
 *
 * @author 益莲科技
 */
@TableName("system_driver")
@KeySequence("system_driver_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 协议名称
     */
    private String name;
    /**
     * 协议服务名称
     */
    private String serviceName;
    /**
     * 类型，driver、gateway...
     */
    private String type;
    /**
     * 主机IP
     */
    private String host;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 是否可用
     *
     * 枚举 {@link cn.iocoder.yudao.module.system.enums.DictTypeConstants#COMMON_STATUS 对应的类}
     */
    private Integer enable;
    /**
     * 描述
     */
    private String description;
    /**
     * 租户ID
     */
    private Long tenantId;

}
