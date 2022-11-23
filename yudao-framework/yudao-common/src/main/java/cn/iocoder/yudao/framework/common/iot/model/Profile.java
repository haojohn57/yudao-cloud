package cn.iocoder.yudao.framework.common.iot.model;

import cn.iocoder.yudao.framework.common.validation.Insert;
import cn.iocoder.yudao.framework.common.validation.Update;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

/**
 * 设备变量表
 *
 * @author 益莲科技
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Profile extends Description {

    @NotBlank(message = "name can't be empty", groups = {Insert.class})
    @Pattern(regexp = "^[A-Za-z0-9\\u4e00-\\u9fa5][A-Za-z0-9\\u4e00-\\u9fa5-_#@/\\.\\|]{1,31}$", message = "Invalid name,contains invalid characters or length is not in the range of 2~32", groups = {Insert.class, Update.class})
    private String name;

    private Boolean share = true;

    private Short type = 1;

    private Boolean enable;

    @TableField(exist = false)
    private Set<Long> pointIds = new HashSet<>(8);

    // TODO 后期再实现分组，先放着占个坑 @NotNull(message = "group id can't be empty", groups = {Insert.class, Update.class})
    private Long deptId;

    private Long deviceId;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Long tenantId;

    public Profile(String name, Boolean share, Long driverId, Long tenantId) {
        this.name = name;
        this.share = share;
        this.tenantId = tenantId;
    }
}
