package cn.iocoder.yudao.module.system.api.iot.driver.dto;

import cn.iocoder.yudao.framework.common.validation.Insert;
import cn.iocoder.yudao.framework.common.validation.Update;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

/**
 * 驱动 Response DTO
 *
 * @author 芋道源码
 */
@Data
public class DriverRespDTO {


    /**
     * 驱动编号
     */
    private Long id;
    /**
     * 驱动名称
     */
    private String name;

    private Integer enable;

    private Long tenantId;

    private String serviceName;

    private String host;

    private String type;

    private Integer port;

    private String status;

}

