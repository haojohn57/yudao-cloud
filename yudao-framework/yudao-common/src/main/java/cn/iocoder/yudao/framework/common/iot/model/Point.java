package cn.iocoder.yudao.framework.common.iot.model;

import cn.iocoder.yudao.framework.common.enums.ValueConstants;
import cn.iocoder.yudao.framework.common.validation.Insert;
import cn.iocoder.yudao.framework.common.validation.Update;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
public class Point extends Description {

    @NotBlank(message = "name can't be empty", groups = {Insert.class})
    @Pattern(regexp = "^[A-Za-z0-9\\u4e00-\\u9fa5][A-Za-z0-9\\u4e00-\\u9fa5-_#@/\\.\\|]{1,31}$", message = "Invalid name,contains invalid characters or length is not in the range of 2~32", groups = {Insert.class, Update.class})
    private String name;

    private String type;
    private Short rw;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Float base;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Float minimum;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Float maximum;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Float multiple;

    private Integer accrue;
    private String format;
    private String unit;

    private Integer enable;

    @NotNull(message = "profile id can't be empty", groups = {Insert.class, Update.class})
    private Long profileId;

    // TODO 后期再实现分组，先放着占个坑 @NotNull(message = "group id can't be empty", groups = {Insert.class, Update.class})
    private Long deptId;

    private Long tenantId;

    public Point(String name, String type, Short rw, Float base, Float minimum, Float maximum, Float multiple,
                 Integer accrue, String format, String unit, Long profileId, Long tenantId) {
        this.name = name;
        this.type = type;
        this.rw = rw;
        this.base = base;
        this.minimum = minimum;
        this.maximum = maximum;
        this.multiple = multiple;
        this.accrue = accrue;
        this.format = format;
        this.unit = unit;
        this.profileId = profileId;
        this.tenantId = tenantId;
    }

    public Point setDefault() {
        this.type = ValueConstants.Type.STRING;
        this.rw = 0;
        this.base = 0F;
        this.minimum = -999999F;
        this.maximum = 999999F;
        this.multiple = 1F;
        this.accrue = 1;
        this.format = "%3.f";
        this.unit = "\"";
        return this;
    }
}
