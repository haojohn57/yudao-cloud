package cn.iocoder.yudao.framework.common.iot.bean.driver;

import cn.iocoder.yudao.framework.common.iot.model.Driver;
import cn.iocoder.yudao.framework.common.iot.model.DriverAttribute;
import cn.iocoder.yudao.framework.common.iot.model.PointAttribute;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author 益莲科技
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DriverRegister implements Serializable {
    private static final long serialVersionUID = 1L;

    private String tenant;
    private Driver driver;
    private List<DriverAttribute> driverAttributes;
    private List<PointAttribute> pointAttributes;

}
