package cn.iocoder.yudao.framework.common.iot.model;

import cn.iocoder.yudao.framework.common.validation.Insert;
import cn.iocoder.yudao.framework.common.validation.Update;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

/**
 * 设备表
 *
 * @author 益莲科技
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Device extends Description {

    @NotBlank(message = "name can't be empty", groups = {Insert.class})
    @Pattern(regexp = "^[A-Za-z0-9\\u4e00-\\u9fa5][A-Za-z0-9\\u4e00-\\u9fa5-_#@/\\.\\|]{1,31}$", message = "Invalid name,contains invalid characters or length is not in the range of 2~32", groups = {Insert.class, Update.class})
    private String name;

    /**
     * 是否结构化存储数据
     * 默认：false，为单点存储
     */
    private Integer multi;

    private Integer enable;

    @TableField(exist = false)
    private Set<Long> profileIds = new HashSet<>(8);

    @NotNull(message = "driver id can't be empty", groups = {Insert.class, Update.class})
    private Long driverId;

    @NotNull(message = "dept id can't be empty", groups = {Insert.class, Update.class})
    private Long deptId;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Long tenantId;

    public Device(String name, Long profileId, Long deptId) {
        super();
        this.name = name;
        //this.profileId = profileId;
        this.deptId = deptId;
    }
}
