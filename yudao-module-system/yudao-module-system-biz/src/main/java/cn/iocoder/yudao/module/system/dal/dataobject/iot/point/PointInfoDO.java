package cn.iocoder.yudao.module.system.dal.dataobject.iot.point;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 位号配置信息 DO
 *
 * @author 益莲科技
 */
@TableName("system_point_info")
@KeySequence("system_point_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointInfoDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 位号配置ID
     */
    private Long pointAttributeId;
    /**
     * 值
     */
    private String value;
    /**
     * 设备ID
     */
    private Long deviceId;
    /**
     * 位号ID
     */
    private Long pointId;
    /**
     * 描述
     */
    private String description;
    /**
     * 租户ID
     */
    private Long tenantId;

}
