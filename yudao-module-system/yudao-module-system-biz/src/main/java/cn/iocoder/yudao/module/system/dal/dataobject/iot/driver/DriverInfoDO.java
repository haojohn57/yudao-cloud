package cn.iocoder.yudao.module.system.dal.dataobject.iot.driver;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 协议配置信息 DO
 *
 * @author 益莲科技
 */
@TableName("system_driver_info")
@KeySequence("system_driver_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverInfoDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 连接配置ID
     */
    private Long driverAttributeId;
    /**
     * 值
     */
    private String value;
    /**
     * 设备ID
     */
    private Long deviceId;
    /**
     * 描述
     */
    private String description;
    /**
     * 租户ID
     */
    private Long tenantId;

}
